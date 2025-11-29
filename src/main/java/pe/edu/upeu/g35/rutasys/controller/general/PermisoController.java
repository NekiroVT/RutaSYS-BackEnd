package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Permiso;
import pe.edu.upeu.g35.rutasys.dto.PermisoDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.PermisoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    // ➡️ POST: /api/permisos (Creación - Retorna 201 CREATED)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Permiso>> createPermiso(@RequestBody Permiso permiso) {
        Permiso newPermiso = permisoService.save(permiso);
        ApiResponseDTO<Permiso> response = ApiResponseDTO.<Permiso>builder()
                .data(newPermiso)
                .status(HttpStatus.CREATED.value())
                .message("Permiso registrado exitosamente.")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ➡️ PUT: /api/permisos/{id} (Actualización Completa - Nuevo Método)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Permiso>> updatePermiso(@PathVariable Long id, @RequestBody Permiso permisoDetails) {
        // Aseguramos que el recurso a actualizar exista
        Permiso existingPermiso = permisoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso con ID " + id + " no encontrado para actualizar."));

        // Aseguramos que el ID del cuerpo sea el mismo que el del path
        permisoDetails.setId(id);

        Permiso updatedPermiso = permisoService.save(permisoDetails);

        ApiResponseDTO<Permiso> response = ApiResponseDTO.<Permiso>builder()
                .data(updatedPermiso)
                .status(HttpStatus.OK.value())
                .message("Permiso actualizado exitosamente.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/permisos (Listar todos)
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PermisoDTO>>> getAllPermisos() {
        List<PermisoDTO> permisos = permisoService.getAllPermisos();
        ApiResponseDTO<List<PermisoDTO>> response = ApiResponseDTO.<List<PermisoDTO>>builder()
                .data(permisos)
                .status(HttpStatus.OK.value())
                .message("Lista de permisos obtenida.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/permisos/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PermisoDTO>> getPermisoById(@PathVariable Long id) {
        PermisoDTO permiso = permisoService.getPermisoDTO(id)
                .orElseThrow(() -> new RuntimeException("Permiso con ID " + id + " no encontrado."));

        ApiResponseDTO<PermisoDTO> response = ApiResponseDTO.<PermisoDTO>builder()
                .data(permiso)
                .status(HttpStatus.OK.value())
                .message("Permiso obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/permisos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermiso(@PathVariable Long id) {
        permisoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}