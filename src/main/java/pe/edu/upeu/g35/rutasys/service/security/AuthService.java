package pe.edu.upeu.g35.rutasys.service.security;

import pe.edu.upeu.g35.rutasys.dto.ChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.AdministradorRegisterRequestDTO; // NECESARIO
import pe.edu.upeu.g35.rutasys.dto.ClienteRegisterRequestDTO; // ⬅️ DTO Cliente
import pe.edu.upeu.g35.rutasys.dto.AyudanteRegisterRequestDTO; // ⬅️ DTO Ayudante
import pe.edu.upeu.g35.rutasys.dto.LoginRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.entity.Administrador; // NECESARIO
import pe.edu.upeu.g35.rutasys.entity.Cliente; // ⬅️ Entidad Cliente
import pe.edu.upeu.g35.rutasys.entity.Ayudante; // ⬅️ Entidad Ayudante
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.AdministradorRepository; // NECESARIO
import pe.edu.upeu.g35.rutasys.repository.ClienteRepository; // ⬅️ REPOSITORIO Cliente
import pe.edu.upeu.g35.rutasys.repository.AyudanteRepository; // ⬅️ AÑADIDO
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
    private final AdministradorRepository administradorRepository;
    private final ClienteRepository clienteRepository;
    private final AyudanteRepository ayudanteRepository;
    private final RolUsuarioRepository rolUsuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String ROL_CHOFER = "CHOFER";
    private static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
    private static final String ROL_CLIENTE = "CLIENTE";
    private static final String ROL_AYUDANTE = "AYUDANTE";

    public AuthService(PasswordEncoder passwordEncoder,
                       UsuarioRepository usuarioRepository,
                       RolRepository rolRepository,
                       ChoferRepository choferRepository,
                       AdministradorRepository administradorRepository,
                       ClienteRepository clienteRepository,
                       AyudanteRepository ayudanteRepository,
                       RolUsuarioRepository rolUsuarioRepository,
                       JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.choferRepository = choferRepository;
        this.administradorRepository = administradorRepository;
        this.clienteRepository = clienteRepository;
        this.ayudanteRepository = ayudanteRepository;
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ------------------------
    // REGISTRO DE UN CHOFER
    // ------------------------
    @Transactional
    public Usuario registerChofer(ChoferRegisterRequestDTO dto) {
        // ... (Implementación de registro omitida por brevedad)
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
    // REGISTRO DE UN ADMINISTRADOR
    // ------------------------------------
    @Transactional
    public Usuario registerAdministrador(AdministradorRegisterRequestDTO dto) {
        // ... (Implementación de registro omitida por brevedad)
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

    // ------------------------------------
    // REGISTRO DE UN CLIENTE
    // ------------------------------------
    @Transactional
    public Usuario registerCliente(ClienteRegisterRequestDTO dto) {
        // ... (Implementación de registro omitida por brevedad)
        Optional<Usuario> existe = usuarioRepository.findByUsername(dto.getUsername());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("El username ya se encuentra en uso.");
        }

        // Validación adicional: Asegurar que el RUC no exista
        if (clienteRepository.findByRuc(dto.getRuc()).isPresent()) {
            throw new IllegalArgumentException("El RUC ya se encuentra registrado.");
        }

        String passHash = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passHash);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // Creamos la entidad Cliente.
        Cliente cliente = Cliente.builder()
                .ruc(dto.getRuc())
                .razonSocial(dto.getRazonSocial())
                .direccion(dto.getDireccion())
                .contacto(dto.getContacto())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .estado("ACTIVO") // Por defecto
                .build();

        clienteRepository.save(cliente);

        // ✅ Asegurar que exista el Rol CLIENTE
        Rol rolCliente = rolRepository.findByNombre(ROL_CLIENTE)
                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(ROL_CLIENTE).build()));

        // ✅ Asignamos el Rol CLIENTE en tabla puente
        rolUsuarioRepository.save(RolUsuario.builder()
                .rol(rolCliente)
                .usuario(nuevoUsuario)
                .build());

        return nuevoUsuario;
    }

    // ------------------------------------
    // REGISTRO DE UN AYUDANTE (NUEVO MÉTODO)
    // ------------------------------------
    @Transactional
    public Usuario registerAyudante(AyudanteRegisterRequestDTO dto) {
        // ... (Implementación de registro omitida por brevedad)
        Optional<Usuario> existe = usuarioRepository.findByUsername(dto.getUsername());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("El username ya se encuentra en uso.");
        }

        // Validación: Asegurar que el DNI no exista (si el DNI es único)
        if (ayudanteRepository.findByDni(dto.getDni()).isPresent()) {
            throw new IllegalArgumentException("El DNI ya se encuentra registrado para otro Ayudante.");
        }

        String passHash = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passHash);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // Creamos la entidad Ayudante asociada al nuevo Usuario
        Ayudante ayudante = Ayudante.builder()
                .usuario(nuevoUsuario)
                .nombreCompleto(dto.getNombreCompleto())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .estado(dto.getEstado() != null ? dto.getEstado() : "ACTIVO") // Usa el estado del DTO o "ACTIVO"
                .build();

        ayudanteRepository.save(ayudante);

        // ✅ Asegurar que exista el Rol AYUDANTE
        Rol rolAyudante = rolRepository.findByNombre(ROL_AYUDANTE)
                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(ROL_AYUDANTE).build()));

        // ✅ Asignamos el Rol AYUDANTE en tabla puente
        rolUsuarioRepository.save(RolUsuario.builder()
                .rol(rolAyudante)
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

        // 🚀 LÓGICA CLAVE: Obtener el ID de la entidad de negocio (Chofer, Cliente, Admin, Ayudante)
        Long entidadId = getEntidadIdByRoles(usuario.getId(), rolesNames);

        // ✅ Generar JWT con todos los roles, incluyendo el ID de la entidad de negocio
        // Se asume que el JwtTokenProvider ya fue actualizado para aceptar el tercer argumento (entidadId)
        return jwtTokenProvider.generateToken(usuario, rolesNames, entidadId);
    }

    /**
     * Helper para obtener el ID de la entidad de negocio (Chofer, Cliente, Admin, Ayudante)
     * basándose en el rol principal del usuario.
     */
    private Long getEntidadIdByRoles(Long usuarioId, List<String> rolesNames) {
        if (rolesNames.isEmpty()) {
            return null;
        }

        // Se usa el primer rol como rol principal para determinar qué entidad buscar.
        String rolPrincipal = rolesNames.get(0);

        if (ROL_CHOFER.equals(rolPrincipal)) {
            return choferRepository.findByUsuario_Id(usuarioId).map(Chofer::getId).orElse(null);
        } else if (ROL_ADMINISTRADOR.equals(rolPrincipal)) {
            return administradorRepository.findByUsuario_Id(usuarioId).map(Administrador::getId).orElse(null);
        } else if (ROL_CLIENTE.equals(rolPrincipal)) {
            // NOTA: Si la entidad Cliente no tiene FK a Usuario (como se infiere de tu código),
            // esta lógica fallará. Asumiendo que la relación es débil o se ignora en el token
            // para el CLIENTE, devolvemos null, pero lo ideal es buscar por una FK.
            return null;
        } else if (ROL_AYUDANTE.equals(rolPrincipal)) {
            return ayudanteRepository.findByUsuario_Id(usuarioId).map(Ayudante::getId).orElse(null);
        }

        return null; // Por defecto o para roles sin entidad asociada (como 'USER' genérico)
    }
}