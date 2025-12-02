package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    /*
     * 🚀 MÉTODOS CRUD HEREDADOS AUTOMÁTICAMENTE DE JpaRepository
     */

    // ➡️ BÚSQUEDA PERSONALIZADA:

    /**
     * Busca un vehículo por su placa única.
     */
    Optional<Vehiculo> findByPlaca(String placa);

    /**
     * Busca vehículos por su estado (ej: 'OPERATIVO', 'MANTENIMIENTO').
     */
    List<Vehiculo> findByEstado(String estado);

    /**
     * Busca vehículos por el ID del almacén base.
     */
    List<Vehiculo> findByAlmacenBase_Id(Long idAlmacen);
}