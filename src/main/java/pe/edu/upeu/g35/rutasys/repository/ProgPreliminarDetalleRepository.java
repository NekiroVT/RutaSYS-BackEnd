package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.ProgPreliminarDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgPreliminarDetalleRepository extends JpaRepository<ProgPreliminarDetalle, Long> {

    // Útil para obtener todas las paradas de una asignación específica de vehículo/manifiesto
    List<ProgPreliminarDetalle> findByManifiestoVehiculoId(Long manifiestoVehiculoId);
}