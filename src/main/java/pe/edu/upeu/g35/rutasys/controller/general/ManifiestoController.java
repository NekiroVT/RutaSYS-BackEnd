package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manifiestos")
public class ManifiestoController {

    private final ManifiestoService manifiestoService;

    public ManifiestoController(ManifiestoService manifiestoService) {
        this.manifiestoService = manifiestoService;
    }

    /**
     * Registra una nueva Solicitud de Manifiesto.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ManifiestoDTO>> registerManifiesto(
            @RequestBody ManifiestoRegisterRequestDTO request) {

        try {
            ManifiestoDTO nuevoManifiesto = manifiestoService.register(request);

            ApiResponseDTO<ManifiestoDTO> response = ApiResponseDTO.<ManifiestoDTO>builder()
                    .data(nuevoManifiesto)
                    .status(HttpStatus.CREATED.value())
                    .message("Solicitud de Manifiesto registrada con éxito.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Manejo de errores de validación (ej. Cliente no encontrado, Código duplicado)
            ApiResponseDTO<ManifiestoDTO> errorResponse = ApiResponseDTO.<ManifiestoDTO>builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error al registrar manifiesto: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los manifiestos registrados.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ManifiestoDTO>>> getAllManifiestos() {

        List<ManifiestoDTO> manifiestos = manifiestoService.getAllManifiestos();

        ApiResponseDTO<List<ManifiestoDTO>> response = ApiResponseDTO.<List<ManifiestoDTO>>builder()
                .data(manifiestos)
                .status(HttpStatus.OK.value())
                .message("Manifiestos obtenidos con éxito.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}