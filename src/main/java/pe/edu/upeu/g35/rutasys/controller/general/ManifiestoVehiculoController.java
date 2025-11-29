package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoVehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/manif-vehiculos")
public class ManifiestoVehiculoController {

    private final ManifiestoVehiculoService mvService;

    public ManifiestoVehiculoController(ManifiestoVehiculoService mvService) {
        this.mvService = mvService;
    }

    // ➡️ POST: /api/manif-vehiculos/assign (Asignar recursos)
    @PostMapping("/assign")
    public ResponseEntity<ApiResponseDTO<ManifiestoVehiculoDTO>> assignResources(@RequestBody ManifiestoVehiculoDTO assignmentDTO) {
        ManifiestoVehiculoDTO newAssignment = mvService.assignResourcesToManifest(assignmentDTO);

        ApiResponseDTO<ManifiestoVehiculoDTO> response = ApiResponseDTO.<ManifiestoVehiculoDTO>builder()
                .data(newAssignment)
                .status(HttpStatus.CREATED.value())
                .message("Recursos asignados al manifiesto exitosamente.")
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ➡️ GET: /api/manif-vehiculos/manifiesto/{id} (Listar por Manifiesto)
    @GetMapping("/manifiesto/{manifiestoId}")
    public ResponseEntity<ApiResponseDTO<List<ManifiestoVehiculoDTO>>> getAssignmentsByManifest(@PathVariable Long manifiestoId) {
        List<ManifiestoVehiculoDTO> assignments = mvService.getAssignmentsByManifestId(manifiestoId);

        ApiResponseDTO<List<ManifiestoVehiculoDTO>> response = ApiResponseDTO.<List<ManifiestoVehiculoDTO>>builder()
                .data(assignments)
                .status(HttpStatus.OK.value())
                .message("Asignaciones obtenidas para el manifiesto ID: " + manifiestoId)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/manif-vehiculos/{id} (Eliminar asignación)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        mvService.delete(id);
        return ResponseEntity.noContent().build();
    }
}