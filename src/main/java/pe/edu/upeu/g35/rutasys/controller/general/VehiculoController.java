package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.VehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.service.VehiculoService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    /**
     * Registra un nuevo Vehículo.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<VehiculoDTO>> registerVehiculo(
            @RequestBody VehiculoRegisterRequestDTO request) {

        try {
            VehiculoDTO nuevoVehiculo = vehiculoService.register(request);

            ApiResponseDTO<VehiculoDTO> response = ApiResponseDTO.<VehiculoDTO>builder()
                    .data(nuevoVehiculo)
                    .status(HttpStatus.CREATED.value())
                    .message("Vehículo registrado con éxito.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Manejo de errores de validación (ej. Placa duplicada, Almacén no encontrado)
            ApiResponseDTO<VehiculoDTO> errorResponse = ApiResponseDTO.<VehiculoDTO>builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error al registrar vehículo: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los vehículos registrados.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<VehiculoDTO>>> getAllVehiculos() {

        List<VehiculoDTO> vehiculos = vehiculoService.getAllVehiculos();

        ApiResponseDTO<List<VehiculoDTO>> response = ApiResponseDTO.<List<VehiculoDTO>>builder()
                .data(vehiculos)
                .status(HttpStatus.OK.value())
                .message("Vehículos obtenidos con éxito.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un vehículo por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<VehiculoDTO>> getVehiculoById(@PathVariable Long id) {

        Optional<VehiculoDTO> vehiculoOpt = vehiculoService.getVehiculoDTO(id);

        if (vehiculoOpt.isPresent()) {
            ApiResponseDTO<VehiculoDTO> response = ApiResponseDTO.<VehiculoDTO>builder()
                    .data(vehiculoOpt.get())
                    .status(HttpStatus.OK.value())
                    .message("Vehículo encontrado.")
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponseDTO<VehiculoDTO> response = ApiResponseDTO.<VehiculoDTO>builder()
                    .data(null)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Vehículo con ID " + id + " no encontrado.")
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un vehículo por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteVehiculo(@PathVariable Long id) {

        try {
            vehiculoService.delete(id);
            ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("Vehículo con ID " + id + " eliminado con éxito.")
                    .success(true)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // NOTA: Para implementar la actualización (PUT/PATCH), se necesitaría un
    // DTO de actualización y un método update() en el servicio.
}