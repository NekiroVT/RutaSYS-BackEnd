package pe.edu.upeu.g35.rutasys.controller.security;

import pe.edu.upeu.g35.rutasys.dto.ChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.AdministradorRegisterRequestDTO; // ⬅️ IMPORTACIÓN NECESARIA
import pe.edu.upeu.g35.rutasys.dto.ClienteRegisterRequestDTO; // ⬅️ AÑADIDO
import pe.edu.upeu.g35.rutasys.dto.AyudanteRegisterRequestDTO; // ⬅️ AÑADIDO
import pe.edu.upeu.g35.rutasys.dto.LoginRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.service.security.AuthService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -------------------------------------------------------------------------
    // 1. ENDPOINT DE REGISTRO DE CHOFER (POST /api/auth/register/chofer)
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo Chofer y su cuenta de Usuario asociada.
     */
    @PostMapping("/register/chofer")
    public ResponseEntity<ApiResponseDTO<Usuario>> registerChofer(
            @RequestBody ChoferRegisterRequestDTO request) {

        try {
            Usuario nuevoUsuario = authService.registerChofer(request);

            ApiResponseDTO<Usuario> response = ApiResponseDTO.<Usuario>builder()
                    .data(nuevoUsuario)
                    .status(HttpStatus.CREATED.value())
                    .message("Registro de Chofer y Usuario exitoso.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Se relanza para ser capturado por el GlobalExceptionHandler
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // 2. ENDPOINT DE REGISTRO DE ADMINISTRADOR (POST /api/auth/register/administrador)
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo Administrador y su cuenta de Usuario asociada.
     */
    @PostMapping("/register/administrador")
    public ResponseEntity<ApiResponseDTO<Usuario>> registerAdministrador(
            @RequestBody AdministradorRegisterRequestDTO request) {

        try {
            Usuario nuevoUsuario = authService.registerAdministrador(request);

            ApiResponseDTO<Usuario> response = ApiResponseDTO.<Usuario>builder()
                    .data(nuevoUsuario)
                    .status(HttpStatus.CREATED.value())
                    .message("Registro de Administrador y Usuario exitoso.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Se relanza para ser capturado por el GlobalExceptionHandler
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // 3. ENDPOINT DE REGISTRO DE CLIENTE (POST /api/auth/register/cliente)
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo Cliente y su cuenta de Usuario asociada.
     */
    @PostMapping("/register/cliente")
    public ResponseEntity<ApiResponseDTO<Usuario>> registerCliente(
            @RequestBody ClienteRegisterRequestDTO request) { // Usa el DTO de Cliente

        try {
            Usuario nuevoUsuario = authService.registerCliente(request); // Llama al método del AuthService

            ApiResponseDTO<Usuario> response = ApiResponseDTO.<Usuario>builder()
                    .data(nuevoUsuario)
                    .status(HttpStatus.CREATED.value())
                    .message("Registro de Cliente y Usuario exitoso.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Se relanza para ser capturado por el GlobalExceptionHandler
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // 4. ENDPOINT DE REGISTRO DE AYUDANTE (POST /api/auth/register/ayudante) ⬅️ AÑADIDO
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo Ayudante y su cuenta de Usuario asociada.
     */
    @PostMapping("/register/ayudante")
    public ResponseEntity<ApiResponseDTO<Usuario>> registerAyudante(
            @RequestBody AyudanteRegisterRequestDTO request) {

        try {
            Usuario nuevoUsuario = authService.registerAyudante(request);

            ApiResponseDTO<Usuario> response = ApiResponseDTO.<Usuario>builder()
                    .data(nuevoUsuario)
                    .status(HttpStatus.CREATED.value())
                    .message("Registro de Ayudante y Usuario exitoso.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Se relanza para ser capturado por el GlobalExceptionHandler
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    // -------------------------------------------------------------------------
    // 5. ENDPOINT DE LOGIN (POST /api/auth/login)
    // -------------------------------------------------------------------------

    /**
     * Inicia sesión, verifica credenciales y devuelve el JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> login(@RequestBody LoginRequestDTO request) {
        try {
            String token = authService.login(request);

            ApiResponseDTO<String> response = ApiResponseDTO.<String>builder()
                    .data(token) // El JWT va en el campo 'data'
                    .status(HttpStatus.OK.value())
                    .message("Inicio de sesión exitoso. Token generado.")
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            ApiResponseDTO<String> errorResponse = ApiResponseDTO.<String>builder()
                    .data(null)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("¡Contraseña o Usuario Incorrecto!")
                    .success(false)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}