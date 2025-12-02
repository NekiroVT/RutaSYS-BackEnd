package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManifiestoRepository extends JpaRepository<Manifiesto, Long> {
    /*
     * 🚀 MÉTODOS CRUD HEREDADOS AUTOMÁTICAMENTE DE JpaRepository:
     * * - Crear/Actualizar: save(Manifiesto entity)
     * - Leer (Por ID): findById(Long id)
     * - Leer (Todos): findAll()
     * - Eliminar: deleteById(Long id)
     * - Verificar existencia: existsById(Long id)
     */

    // ➡️ BÚSQUEDA PERSONALIZADA:

    /**
     * Busca un manifiesto por su código único.
     */
    Optional<Manifiesto> findByCodManifiesto(String codManifiesto);

    /**
     * Busca manifiestos por el ID del cliente.
     */
    List<Manifiesto> findByCliente_Id(Long idCliente);

    /**
     * Busca manifiestos por el estado actual.
     */
    List<Manifiesto> findByEstado(String estado);
}