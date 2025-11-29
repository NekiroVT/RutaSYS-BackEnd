package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long> {

    // Método para encontrar todos los roles asignados a un Usuario
    List<RolUsuario> findByUsuarioId(Long usuarioId);

    // Método para verificar si una asignación específica existe
    boolean existsByRolIdAndUsuarioId(Long rolId, Long usuarioId);
}