package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import pe.edu.upeu.g35.rutasys.dto.RolUsuarioDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional; // ⬅️ IMPORTACIÓN NECESARIA

public interface RolUsuarioService extends GenericService<RolUsuario, Long> {

    // --- NUEVO MÉTODO AÑADIDO PARA SOPORTAR EL GET DEL CONTROLADOR ---
    Optional<RolUsuarioDTO> getRolUsuarioDTO(Long id);
    // ------------------------------------------------------------------

    // Método para crear una nueva asignación de rol a un usuario
    RolUsuarioDTO assignRoleToUser(Long rolId, Long usuarioId);

    // Método para eliminar una asignación de rol específica
    void removeRoleFromUser(Long rolId, Long usuarioId);

    // Método para listar todos los roles asignados a un usuario específico
    List<RolUsuarioDTO> getRolesByUserId(Long usuarioId);
}