package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManifiestoVehiculoRepository extends JpaRepository<ManifiestoVehiculo, Long> {

    // ➡️ BÚSQUEDA PERSONALIZADA:

    /**
     * Busca asignaciones por ID de Manifiesto.
     */
    List<ManifiestoVehiculo> findByManifiesto_Id(Long idManifiesto);

    /**
     * Busca asignaciones por ID de Vehículo.
     */
    List<ManifiestoVehiculo> findByVehiculo_Id(Long idVehiculo);

    /**
     * Busca la asignación activa (o por estado) para un vehículo específico.
     */
    List<ManifiestoVehiculo> findByVehiculo_IdAndEstado(Long idVehiculo, String estado);
}