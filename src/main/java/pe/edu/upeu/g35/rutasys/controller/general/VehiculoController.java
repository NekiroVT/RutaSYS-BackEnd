package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // ➡️ POST: /api/vehiculos (Creación)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Vehiculo>> createVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo newVehiculo = vehiculoService.save(vehiculo);

        ApiResponseDTO<Vehiculo> response = ApiResponseDTO.<Vehiculo>builder()
                .data(newVehiculo)
                .status(HttpStatus.CREATED.value())
                .message("Vehículo registrado exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➡️ PUT: /api/vehiculos/{id} (Actualización)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Vehiculo>> updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoDetails) {

        // Aseguramos que el ID del path se use en la entidad
        vehiculoDetails.setId(id);
        Vehiculo updatedVehiculo = vehiculoService.save(vehiculoDetails);

        ApiResponseDTO<Vehiculo> response = ApiResponseDTO.<Vehiculo>builder()
                .data(updatedVehiculo)
                .status(HttpStatus.OK.value())
                .message("Vehículo actualizado exitosamente.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/vehiculos (Listar todos)
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<VehiculoDTO>>> getAllVehiculos() {
        List<VehiculoDTO> vehiculos = vehiculoService.getAllVehiculos();

        ApiResponseDTO<List<VehiculoDTO>> response = ApiResponseDTO.<List<VehiculoDTO>>builder()
                .data(vehiculos)
                .status(HttpStatus.OK.value())
                .message("Lista de vehículos obtenida.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/vehiculos/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<VehiculoDTO>> getVehiculoById(@PathVariable Long id) {
        VehiculoDTO vehiculo = vehiculoService.getVehiculoDTO(id)
                .orElseThrow(() -> new RuntimeException("Vehículo con ID " + id + " no encontrado."));

        ApiResponseDTO<VehiculoDTO> response = ApiResponseDTO.<VehiculoDTO>builder()
                .data(vehiculo)
                .status(HttpStatus.OK.value())
                .message("Vehículo obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/vehiculos/{id} (Eliminación con cuerpo estructurado usando sintaxis fluida)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteVehiculo(@PathVariable Long id) {
        vehiculoService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value()) // Se usa 200 OK para permitir el cuerpo JSON
                .message("Vehículo eliminado exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}