package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.dto.RolDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

// Hereda CRUD básico: save(), findById(), delete(), findAll()
public interface RolService extends GenericService<Rol, Long> {

    // 1. Lectura que devuelve DTO
    Optional<RolDTO> getRolDTO(Long id);

    // 2. Obtener lista de todos los roles como DTO
    List<RolDTO> getAllRoles();

    // 3. Método para buscar por nombre (útil para el AuthController)
    Optional<Rol> findByNombre(String nombre);

    // (Aquí iría la lógica para asignar un permiso a un rol, si fuera necesario)
}