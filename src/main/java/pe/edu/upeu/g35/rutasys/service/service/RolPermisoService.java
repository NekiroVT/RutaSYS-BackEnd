package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.RolPermiso;
import pe.edu.upeu.g35.rutasys.dto.RolPermisoDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface RolPermisoService extends GenericService<RolPermiso, Long> {

    // Método para asignar un permiso a un rol
    RolPermisoDTO assignPermissionToRole(Long rolId, Long permisoId);

    // Método para remover un permiso específico de un rol
    void removePermissionFromRole(Long rolId, Long permisoId);

    // Método para listar todos los permisos asignados a un rol específico
    List<RolPermisoDTO> getPermissionsByRoleId(Long rolId);

    // Método para obtener una asignación específica por ID (para el CRUD estándar)
    Optional<RolPermisoDTO> getRolPermisoDTO(Long id);
}