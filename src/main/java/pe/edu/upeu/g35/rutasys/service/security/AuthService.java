package pe.edu.upeu.g35.rutasys.service.security;

import pe.edu.upeu.g35.rutasys.dto.ChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.LoginRequestDTO; // ⬅️ Nuevo para Login
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;
import pe.edu.upeu.g35.rutasys.repository.RolRepository;
import org.springframework.security.authentication.BadCredentialsException; // ⬅️ Nuevo para Login
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ChoferRepository choferRepository;
    private final JwtTokenProvider jwtTokenProvider; // ⬅️ INYECCIÓN DE JWT

    private static final String ROL_CHOFER_NOMBRE = "CHOFER";

    public AuthService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository,
                       RolRepository rolRepository, ChoferRepository choferRepository,
                       JwtTokenProvider jwtTokenProvider) { // ⬅️ Constructor Actualizado
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.choferRepository = choferRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // --- 1. REGISTRO DE CHOFER (Mantenido) ---

    @Transactional
    public Usuario registerChofer(ChoferRegisterRequestDTO dto) {

        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El username ya se encuentra en uso.");
        }

        // BUSCAR O CREAR EL ROL "CHOFER"
        Rol rolChofer = rolRepository.findByNombre(ROL_CHOFER_NOMBRE)
                .orElseGet(() -> {
                    Rol nuevoRol = Rol.builder().nombre(ROL_CHOFER_NOMBRE).build();
                    return rolRepository.save(nuevoRol);
                });

        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(hashedPassword);
        usuario.setRol(rolChofer);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // Crear Chofer asociado
        Chofer chofer = Chofer.builder()
                .usuario(nuevoUsuario)
                .nombreCompleto(dto.getNombreCompleto())
                .dni(dto.getDni())
                .licencia(dto.getLicencia())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .estado("ACTIVO")
                .build();

        choferRepository.save(chofer);

        return nuevoUsuario;
    }

    // --- 2. INICIO DE SESIÓN (NUEVO) ---

    @Transactional(readOnly = true)
    public String login(LoginRequestDTO request) {

        // 1. Buscar el usuario
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Nombre de usuario o contraseña incorrectos."));

        // 2. Verificar la Contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Nombre de usuario o contraseña incorrectos.");
        }

        // 3. Generar el JWT usando el proveedor
        String token = jwtTokenProvider.generateToken(usuario);

        return token;
    }
}