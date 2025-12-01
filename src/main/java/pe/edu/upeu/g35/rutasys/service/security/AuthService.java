package pe.edu.upeu.g35.rutasys.service.security;

import pe.edu.upeu.g35.rutasys.dto.ChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.AdministradorRegisterRequestDTO; // NECESARIO
import pe.edu.upeu.g35.rutasys.dto.LoginRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.entity.Administrador; // NECESARIO
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.AdministradorRepository; // NECESARIO
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;
import pe.edu.upeu.g35.rutasys.repository.RolRepository;
import pe.edu.upeu.g35.rutasys.repository.RolUsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.upeu.g35.rutasys.entity.Rol;


import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ChoferRepository choferRepository;
    private final AdministradorRepository administradorRepository; // AÑADIDO
    private final RolUsuarioRepository rolUsuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String ROL_CHOFER = "CHOFER";
    private static final String ROL_ADMINISTRADOR = "ADMINISTRADOR"; // AÑADIDO

    public AuthService(PasswordEncoder passwordEncoder,
                       UsuarioRepository usuarioRepository,
                       RolRepository rolRepository,
                       ChoferRepository choferRepository,
                       AdministradorRepository administradorRepository, // AÑADIDO
                       RolUsuarioRepository rolUsuarioRepository,
                       JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.choferRepository = choferRepository;
        this.administradorRepository = administradorRepository; // ASIGNACIÓN
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ------------------------
    // REGISTRO DE UN CHOFER
    // ------------------------
    @Transactional
    public Usuario registerChofer(ChoferRegisterRequestDTO dto) {

        Optional<Usuario> existe = usuarioRepository.findByUsername(dto.getUsername());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("El username ya se encuentra en uso.");
        }

        String passHash = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passHash);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        Chofer chofer = Chofer.builder()
                .usuario(nuevoUsuario)
                .nombreCompleto(dto.getNombreCompleto())
                .dni(dto.getDni())
                .licencia(dto.getLicencia())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .estado("ACTIVO")
                .imagenUrl(dto.getImagenUrl())
                .build();

        choferRepository.save(chofer);

        // ✅ Asegurar que exista el Rol CHOFER
        Rol rolChofer = rolRepository.findByNombre(ROL_CHOFER)
                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(ROL_CHOFER).build()));

        // ✅ Asignamos el Rol CHOFER en tabla puente
        rolUsuarioRepository.save(RolUsuario.builder()
                .rol(rolChofer)
                .usuario(nuevoUsuario)
                .build());

        return nuevoUsuario;
    }

    // ------------------------------------
    // REGISTRO DE UN ADMINISTRADOR (NUEVO MÉTODO)
    // ------------------------------------
    @Transactional
    public Usuario registerAdministrador(AdministradorRegisterRequestDTO dto) {

        Optional<Usuario> existe = usuarioRepository.findByUsername(dto.getUsername());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("El username ya se encuentra en uso.");
        }

        String passHash = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passHash);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        Administrador administrador = Administrador.builder()
                .usuario(nuevoUsuario)
                .nombreCompleto(dto.getNombreCompleto())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .cargo(dto.getCargo())
                .estado("ACTIVO") // Por defecto
                .build();

        administradorRepository.save(administrador); // Usando AdministradorRepository

        // ✅ Asegurar que exista el Rol ADMINISTRADOR
        Rol rolAdmin = rolRepository.findByNombre(ROL_ADMINISTRADOR)
                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(ROL_ADMINISTRADOR).build()));

        // ✅ Asignamos el Rol ADMINISTRADOR en tabla puente
        rolUsuarioRepository.save(RolUsuario.builder()
                .rol(rolAdmin)
                .usuario(nuevoUsuario)
                .build());

        return nuevoUsuario;
    }

    // ---------------
    // LOGIN → JWT con todos los roles
    // ---------------
    @Transactional(readOnly = true)
    public String login(LoginRequestDTO request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Credenciales incorrectas."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Credenciales incorrectas.");
        }

        // ✅ Cargar roles del usuario desde ROL_USUARIO
        List<RolUsuario> roles = rolUsuarioRepository.findByUsuarioId(usuario.getId());
        if (roles.isEmpty()) {
            throw new RuntimeException("El usuario no tiene roles asignados.");
        }

        // ✅ Convertir a lista de nombres de rol
        List<String> rolesNames = roles.stream()
                .map(ru -> ru.getRol().getNombre().toUpperCase())
                .toList();

        // ✅ Generar JWT con todos los roles
        return jwtTokenProvider.generateToken(usuario, rolesNames);
    }
}