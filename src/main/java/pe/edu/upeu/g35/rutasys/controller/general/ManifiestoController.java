package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoService;
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

    // ➡️ POST: /api/manifiestos (Creación)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ManifiestoDTO>> createManifiesto(@RequestBody Manifiesto manifiesto) {
        ManifiestoDTO newManifiesto = manifiestoService.saveManifiesto(manifiesto);

        ApiResponseDTO<ManifiestoDTO> response = ApiResponseDTO.<ManifiestoDTO>builder()
                .data(newManifiesto)
                .status(HttpStatus.CREATED.value())
                .message("Manifiesto registrado exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➡️ PUT: /api/manifiestos/{id} (Actualización Completa)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ManifiestoDTO>> updateManifiesto(@PathVariable Long id, @RequestBody Manifiesto manifiestoDetails) {

        manifiestoDetails.setId(id);
        ManifiestoDTO updatedManifiesto = manifiestoService.saveManifiesto(manifiestoDetails);

        ApiResponseDTO<ManifiestoDTO> response = ApiResponseDTO.<ManifiestoDTO>builder()
                .data(updatedManifiesto)
                .status(HttpStatus.OK.value())
                .message("Manifiesto actualizado exitosamente.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/manifiestos (Listar todos)
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ManifiestoDTO>>> getAllManifiestos() {
        List<ManifiestoDTO> manifiestos = manifiestoService.getAllManifiestos();

        ApiResponseDTO<List<ManifiestoDTO>> response = ApiResponseDTO.<List<ManifiestoDTO>>builder()
                .data(manifiestos)
                .status(HttpStatus.OK.value())
                .message("Lista de manifiestos obtenida.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/manifiestos/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ManifiestoDTO>> getManifiestoById(@PathVariable Long id) {
        ManifiestoDTO manifiesto = manifiestoService.getManifiestoDTO(id)
                .orElseThrow(() -> new RuntimeException("Manifiesto con ID " + id + " no encontrado."));

        ApiResponseDTO<ManifiestoDTO> response = ApiResponseDTO.<ManifiestoDTO>builder()
                .data(manifiesto)
                .status(HttpStatus.OK.value())
                .message("Manifiesto obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/manifiestos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteManifiesto(@PathVariable Long id) {
        manifiestoService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Manifiesto eliminado exitosamente.")
                .success(true)
                .build();

        // Usa ResponseEntity.ok() para devolver el JSON con código 200
        return ResponseEntity.ok(response);
    }
}