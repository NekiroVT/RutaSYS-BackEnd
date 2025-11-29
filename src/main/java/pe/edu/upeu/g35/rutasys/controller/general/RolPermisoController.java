package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.RolPermisoDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.RolPermisoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rol-permisos")
public class RolPermisoController {

    private final RolPermisoService rolPermisoService;

    public RolPermisoController(RolPermisoService rolPermisoService) {
        this.rolPermisoService = rolPermisoService;
    }

    // -------------------------------------------------------------------------
    // 1. ASIGNAR PERMISO (CREATE)
    // -------------------------------------------------------------------------

    /**
     * POST /api/rol-permisos/assign
     * Asigna un permiso a un rol específico.
     */
    @PostMapping("/assign")
    public ResponseEntity<ApiResponseDTO<RolPermisoDTO>> assignPermission(@RequestBody RolPermisoDTO request) {
        // Llama al método de servicio que maneja la lógica de asignación
        RolPermisoDTO newAssignment = rolPermisoService.assignPermissionToRole(
                request.getRolId(),
                request.getPermisoId()
        );

        ApiResponseDTO<RolPermisoDTO> response = ApiResponseDTO.<RolPermisoDTO>builder()
                .data(newAssignment)
                .status(HttpStatus.CREATED.value())
                .message("Permiso asignado al rol exitosamente.")
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // -------------------------------------------------------------------------
    // 2. REVOCAR PERMISO (DELETE)
    // -------------------------------------------------------------------------

    /**
     * DELETE /api/rol-permisos/remove
     * Revoca un permiso de un rol específico.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponseDTO<Void>> removePermission(@RequestBody RolPermisoDTO request) {
        rolPermisoService.removePermissionFromRole(
                request.getRolId(),
                request.getPermisoId()
        );

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.NO_CONTENT.value())
                .message("Permiso revocado exitosamente.")
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    // -------------------------------------------------------------------------
    // 3. CONSULTAR PERMISOS POR ROL (READ LIST)
    // -------------------------------------------------------------------------

    /**
     * GET /api/rol-permisos/role/{rolId}
     * Lista todos los permisos asignados a un rol específico.
     */
    @GetMapping("/role/{rolId}")
    public ResponseEntity<ApiResponseDTO<List<RolPermisoDTO>>> getPermissionsByRoleId(@PathVariable Long rolId) {
        List<RolPermisoDTO> permissions = rolPermisoService.getPermissionsByRoleId(rolId);

        ApiResponseDTO<List<RolPermisoDTO>> response = ApiResponseDTO.<List<RolPermisoDTO>>builder()
                .data(permissions)
                .status(HttpStatus.OK.value())
                .message("Permisos obtenidos para el Rol ID: " + rolId)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // 4. CONSULTAR ASIGNACIÓN POR ID (READ SINGLE)
    // -------------------------------------------------------------------------

    /**
     * GET /api/rol-permisos/{id}
     * Obtiene una asignación específica por el ID primario de la tabla RolPermiso.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RolPermisoDTO>> getRolPermisoById(@PathVariable Long id) {
        RolPermisoDTO assignment = rolPermisoService.getRolPermisoDTO(id)
                .orElseThrow(() -> new RuntimeException("Asignación de permiso con ID " + id + " no encontrada."));

        ApiResponseDTO<RolPermisoDTO> response = ApiResponseDTO.<RolPermisoDTO>builder()
                .data(assignment)
                .status(HttpStatus.OK.value())
                .message("Asignación obtenida.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}