package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.service.service.AyudanteService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO; // Asumo que utilizas esta clase
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ayudantes")
public class AyudanteController {

    private final AyudanteService ayudanteService;

    @Autowired
    public AyudanteController(AyudanteService ayudanteService) {
        this.ayudanteService = ayudanteService;
    }

    // -------------------------------------------------------------------------
    // 1. CREATE: Registra un nuevo Ayudante (asumiendo que el Usuario ya existe)
    // -------------------------------------------------------------------------

    /**
     * Registra el Ayudante. Este endpoint es típicamente usado para actualizar
     * detalles, mientras que el registro inicial (Usuario + Ayudante) usa AuthController.
     * Si se usa aquí, asume que la FK de Usuario ya viene en la entidad.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AyudanteDTO>> createAyudante(@RequestBody Ayudante ayudante) {

        try {
            // Utilizamos un método en el servicio para manejar la lógica de registro/validación
            AyudanteDTO newAyudante = ayudanteService.registerAyudante(ayudante);

            ApiResponseDTO<AyudanteDTO> response = ApiResponseDTO.<AyudanteDTO>builder()
                    .data(newAyudante)
                    .status(HttpStatus.CREATED.value())
                    .message("Ayudante registrado/guardado exitosamente.")
                    .success(true)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            // Manejo de errores de unicidad o FK
            ApiResponseDTO<AyudanteDTO> errorResponse = ApiResponseDTO.<AyudanteDTO>builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error al registrar ayudante: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // -------------------------------------------------------------------------
    // 2. READ ALL: Obtiene todos los ayudantes
    // -------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AyudanteDTO>>> getAllAyudantes() {
        List<AyudanteDTO> ayudantes = ayudanteService.getAllAyudantes();

        ApiResponseDTO<List<AyudanteDTO>> response = ApiResponseDTO.<List<AyudanteDTO>>builder()
                .data(ayudantes)
                .status(HttpStatus.OK.value())
                .message("Ayudantes obtenidos con éxito.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // 3. READ BY ID: Obtiene un ayudante por ID
    // -------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AyudanteDTO>> getAyudanteById(@PathVariable Long id) {
        // Se usa getAyudanteDTO(Long id) del servicio que devuelve Optional
        return ayudanteService.getAyudanteDTO(id)
                .map(ayudante -> {
                    ApiResponseDTO<AyudanteDTO> response = ApiResponseDTO.<AyudanteDTO>builder()
                            .data(ayudante)
                            .status(HttpStatus.OK.value())
                            .message("Ayudante encontrado.")
                            .success(true)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponseDTO<AyudanteDTO> errorResponse = ApiResponseDTO.<AyudanteDTO>builder()
                            .data(null)
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Ayudante con ID " + id + " no encontrado.")
                            .success(false)
                            .build();
                    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
                });
    }

    // -------------------------------------------------------------------------
    // 4. DELETE: Elimina un ayudante por ID
    // -------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAyudante(@PathVariable Long id) {
        try {
            ayudanteService.delete(id); // Usa el método heredado de GenericService

            ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("Ayudante eliminado con éxito.")
                    .success(true)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            // Captura la excepción lanzada en el servicio si el ID no existe
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}