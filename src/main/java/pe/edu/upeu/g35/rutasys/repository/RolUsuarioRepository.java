package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long> {

    boolean existsByRolIdAndUsuarioId(Long rolId, Long usuarioId);

    List<RolUsuario> findByUsuarioId(Long usuarioId);

    // 🟢 NUEVO: borra SOLO la fila que coincida con ese rol y usuario
    int deleteByRol_IdAndUsuario_Id(Long rolId, Long usuarioId);
}
