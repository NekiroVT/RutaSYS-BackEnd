package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.RecepcionTienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecepcionTiendaRepository extends JpaRepository<RecepcionTienda, Long> {
    // Útil para buscar si ya existe un registro de recepción para un detalle de parada.
    Optional<RecepcionTienda> findByProgPreliminarDetalleId(Long progPreliminarDetalleId);
}