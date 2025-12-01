package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.RolUsuarioDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.RolUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol-usuarios")
public class RolUsuarioController {

    private final RolUsuarioService rolUsuarioService;

    public RolUsuarioController(RolUsuarioService rolUsuarioService) {
        this.rolUsuarioService = rolUsuarioService;
    }

    // -------------------------------------------------------------------------
    // 1. ASIGNAR ROL A USUARIO (POST /assign)
    // -------------------------------------------------------------------------
    @PostMapping("/assign")
    public ResponseEntity<ApiResponseDTO<RolUsuarioDTO>> assignRole(@RequestBody RolUsuarioDTO request) {

        RolUsuarioDTO newAssignment = rolUsuarioService.assignRoleToUser(
                request.getRolId(),
                request.getUsuarioId()
        );

        ApiResponseDTO<RolUsuarioDTO> response = ApiResponseDTO.<RolUsuarioDTO>builder()
                .data(newAssignment)
                .status(HttpStatus.CREATED.value())
                .message("Rol asignado exitosamente al usuario.")
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // -------------------------------------------------------------------------
    // 2. OBTENER UNA ASIGNACIÓN POR ID (GET /{id})
    // -------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RolUsuarioDTO>> getRolUsuarioById(@PathVariable Long id) {

        RolUsuarioDTO assignment = rolUsuarioService.getRolUsuarioDTO(id)
                .orElseThrow(() -> new RuntimeException(
                        "Asignación de rol con ID " + id + " no encontrada.")
                );

        ApiResponseDTO<RolUsuarioDTO> response = ApiResponseDTO.<RolUsuarioDTO>builder()
                .data(assignment)
                .status(HttpStatus.OK.value())
                .message("Asignación obtenida.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // 3. LISTAR ROLES DE UN USUARIO (GET /user/{usuarioId})
    // -------------------------------------------------------------------------
    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<ApiResponseDTO<List<RolUsuarioDTO>>> getRolesByUserId(@PathVariable Long usuarioId) {

        List<RolUsuarioDTO> roles = rolUsuarioService.getRolesByUserId(usuarioId);

        ApiResponseDTO<List<RolUsuarioDTO>> response = ApiResponseDTO.<List<RolUsuarioDTO>>builder()
                .data(roles)
                .status(HttpStatus.OK.value())
                .message("Roles obtenidos para el usuario ID: " + usuarioId)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // 4. REVOCAR UN ROL ESPECÍFICO DE UN USUARIO (DELETE /remove)
    // -------------------------------------------------------------------------
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponseDTO<Void>> removeRole(@RequestBody RolUsuarioDTO request) {

        rolUsuarioService.removeRoleFromUser(
                request.getRolId(),
                request.getUsuarioId()
        );

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.NO_CONTENT.value())
                .message("Asignación de rol removida exitosamente.")
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
