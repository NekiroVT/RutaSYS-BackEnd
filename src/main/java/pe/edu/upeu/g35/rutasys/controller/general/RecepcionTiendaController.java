package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.RecepcionTienda;
import pe.edu.upeu.g35.rutasys.dto.RecepcionTiendaDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.RecepcionTiendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recepciones-tienda")
public class RecepcionTiendaController {

    private final RecepcionTiendaService recepcionService;

    public RecepcionTiendaController(RecepcionTiendaService recepcionService) {
        this.recepcionService = recepcionService;
    }

    // 1. CREATE: POST / (Registrar recepción)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<RecepcionTiendaDTO>> registrarRecepcion(@RequestBody RecepcionTienda recepcionTienda) {
        RecepcionTiendaDTO newRecepcion = recepcionService.registrarRecepcion(recepcionTienda);

        ApiResponseDTO<RecepcionTiendaDTO> response = ApiResponseDTO.<RecepcionTiendaDTO>builder()
                .data(newRecepcion)
                .status(HttpStatus.CREATED.value())
                .message("Recepción de carga registrada exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. READ: GET / (Listar todos) ⬅️ MÉTODO AÑADIDO
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RecepcionTienda>>> getAllRecepciones() {
        // Usamos findAll() heredado, que devuelve la entidad, no el DTO (podrías crear un método DTO en el servicio)
        List<RecepcionTienda> recepciones = recepcionService.findAll();

        ApiResponseDTO<List<RecepcionTienda>> response = ApiResponseDTO.<List<RecepcionTienda>>builder()
                .data(recepciones)
                .status(HttpStatus.OK.value())
                .message("Lista de registros de recepción obtenida.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 3. READ: GET /detalle/{detalleId} (Buscar por ID de Parada - Mantenido)
    @GetMapping("/detalle/{detalleId}")
    public ResponseEntity<ApiResponseDTO<RecepcionTiendaDTO>> getRecepcionByDetalleId(@PathVariable Long detalleId) {
        RecepcionTiendaDTO recepcion = recepcionService.getRecepcionByDetalleId(detalleId)
                .orElseThrow(() -> new RuntimeException("Registro de recepción no encontrado para el detalle ID: " + detalleId));

        ApiResponseDTO<RecepcionTiendaDTO> response = ApiResponseDTO.<RecepcionTiendaDTO>builder()
                .data(recepcion)
                .status(HttpStatus.OK.value())
                .message("Registro de recepción obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 4. UPDATE: PUT /{id} ⬅️ MÉTODO AÑADIDO
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RecepcionTienda>> updateRecepcion(@PathVariable Long id, @RequestBody RecepcionTienda recepcionDetails) {

        // El método save() heredado maneja la actualización
        recepcionDetails.setId(id);
        RecepcionTienda updatedRecepcion = recepcionService.save(recepcionDetails);

        ApiResponseDTO<RecepcionTienda> response = ApiResponseDTO.<RecepcionTienda>builder()
                .data(updatedRecepcion)
                .status(HttpStatus.OK.value())
                .message("Registro de recepción actualizado.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 5. DELETE: DELETE /{id} ⬅️ MÉTODO AÑADIDO
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteRecepcion(@PathVariable Long id) {
        recepcionService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Registro de recepción eliminado exitosamente.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}