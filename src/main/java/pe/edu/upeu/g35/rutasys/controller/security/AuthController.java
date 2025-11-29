package pe.edu.upeu.g35.rutasys.controller.security;

import pe.edu.upeu.g35.rutasys.dto.ChoferRegisterRequestDTO;
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
    // 1. ENDPOINT DE REGISTRO (POST /api/auth/register/chofer)
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo Chofer y su cuenta de Usuario asociada, devolviendo la entidad Usuario creada.
     */
    @PostMapping("/register/chofer")
    public ResponseEntity<ApiResponseDTO<Usuario>> registerChofer(@RequestBody ChoferRegisterRequestDTO request) {
        try {
            Usuario nuevoUsuario = authService.registerChofer(request);

            // Construye la respuesta exitosa (HTTP 201 Created)
            ApiResponseDTO<Usuario> response = ApiResponseDTO.<Usuario>builder()
                    .data(nuevoUsuario)
                    .status(HttpStatus.CREATED.value())
                    .message("Registro de Chofer y Usuario exitoso.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Maneja errores de negocio (Rol no encontrado, username duplicado)
            // Se relanza la excepción que será capturada por el GlobalExceptionHandler (devuelve 400 BAD_REQUEST)
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // 2. ENDPOINT DE LOGIN (POST /api/auth/login)
    // -------------------------------------------------------------------------

    /**
     * Inicia sesión, verifica credenciales y devuelve el JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> login(@RequestBody LoginRequestDTO request) {
        try {
            String token = authService.login(request);

            // Construye la respuesta exitosa (HTTP 200 OK)
            ApiResponseDTO<String> response = ApiResponseDTO.<String>builder()
                    .data(token) // El JWT va en el campo 'data'
                    .status(HttpStatus.OK.value())
                    .message("Inicio de sesión exitoso. Token generado.")
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            // 🛑 MANEJO LOCAL DE CREDENCIALES INVÁLIDAS
            // Se captura BadCredentialsException y se devuelve un 401 UNAUTHORIZED estructurado,
            // evitando que el filtro de seguridad lo intercepte y devuelva el mensaje genérico de "Token inválido".
            ApiResponseDTO<String> errorResponse = ApiResponseDTO.<String>builder()
                    .data(null)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Usuario o contraseña incorrectos.") // ⬅️ Mensaje específico
                    .success(false)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}