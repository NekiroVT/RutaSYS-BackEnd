package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoVehiculoService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones")
public class ManifiestoVehiculoController {

    private final ManifiestoVehiculoService mvService;

    public ManifiestoVehiculoController(ManifiestoVehiculoService mvService) {
        this.mvService = mvService;
    }

    /**
     * 1. CREATE: Registra una nueva asignación de Manifiesto a Vehículo.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ManifiestoVehiculoDTO>> registerAsignacion(
            @RequestBody ManifiestoVehiculoRegisterRequestDTO request) {

        try {
            ManifiestoVehiculoDTO nuevaAsignacion = mvService.register(request);

            ApiResponseDTO<ManifiestoVehiculoDTO> response = ApiResponseDTO.<ManifiestoVehiculoDTO>builder()
                    .data(nuevaAsignacion)
                    .status(HttpStatus.CREATED.value())
                    .message("Asignación de recursos registrada con éxito.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            ApiResponseDTO<ManifiestoVehiculoDTO> errorResponse = ApiResponseDTO.<ManifiestoVehiculoDTO>builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error al asignar recursos: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 2. READ ALL: Obtiene todas las asignaciones.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ManifiestoVehiculoDTO>>> getAllAsignaciones() {

        List<ManifiestoVehiculoDTO> asignaciones = mvService.getAllManifiestoVehiculos();

        ApiResponseDTO<List<ManifiestoVehiculoDTO>> response = ApiResponseDTO.<List<ManifiestoVehiculoDTO>>builder()
                .data(asignaciones)
                .status(HttpStatus.OK.value())
                .message("Asignaciones obtenidas con éxito.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 2. READ BY ID: Obtiene una asignación por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ManifiestoVehiculoDTO>> getAsignacionById(@PathVariable Long id) {

        Optional<ManifiestoVehiculoDTO> asignacionOpt = mvService.getManifiestoVehiculoDTO(id);

        if (asignacionOpt.isPresent()) {
            ApiResponseDTO<ManifiestoVehiculoDTO> response = ApiResponseDTO.<ManifiestoVehiculoDTO>builder()
                    .data(asignacionOpt.get())
                    .status(HttpStatus.OK.value())
                    .message("Asignación encontrada.")
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponseDTO<ManifiestoVehiculoDTO> response = ApiResponseDTO.<ManifiestoVehiculoDTO>builder()
                    .data(null)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Asignación con ID " + id + " no encontrada.")
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}