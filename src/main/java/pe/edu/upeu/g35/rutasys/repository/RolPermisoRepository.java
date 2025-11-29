package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {

    // Método para encontrar todos los permisos asignados a un Rol
    List<RolPermiso> findByRolId(Long rolId);

    // Método para verificar si una asignación específica existe
    boolean existsByRolIdAndPermisoId(Long rolId, Long permisoId);
}