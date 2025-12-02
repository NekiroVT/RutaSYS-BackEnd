package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.AlmacenBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlmacenBaseRepository extends JpaRepository<AlmacenBase, Long> {

    /*
     * 🚀 MÉTODOS CRUD HEREDADOS AUTOMÁTICAMENTE DE JpaRepository
     */

    // ➡️ BÚSQUEDA PERSONALIZADA:

    /**
     * Busca un almacén por su nombre único.
     */
    Optional<AlmacenBase> findByNombre(String nombre);

    /**
     * Busca almacenes por su estado (ej: 'ACTIVO', 'INACTIVO').
     */
    List<AlmacenBase> findByEstado(String estado);
}