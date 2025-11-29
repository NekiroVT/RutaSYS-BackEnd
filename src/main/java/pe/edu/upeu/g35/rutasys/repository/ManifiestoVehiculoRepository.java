package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upeu.g35.rutasys.dto.DocumentationStopViewDTO; // DTO de la Vista
import java.util.List;

@Repository
public interface ManifiestoVehiculoRepository extends JpaRepository<ManifiestoVehiculo, Long> {

    // 1. MÉTODO DE BÚSQUEDA SIMPLE (Para lógica de negocio del servicio)
    List<ManifiestoVehiculo> findByManifiestoId(Long manifiestoId);

    // ----------------------------------------------------------------------------------------------------

    // 2. MÉTODO DE CONSULTA COMPLEJA (Para armar la tabla del Dashboard)
    @Query("""
        SELECT new pe.edu.upeu.g35.rutasys.dto.DocumentationStopViewDTO( 
            m.codManifiesto, 
            v.placa, 
            t.nombreTienda, 
            d.estado, 
            r.fechaHoraRecepcion 
        )
        FROM ManifiestoVehiculo mv
        JOIN mv.manifiesto m
        JOIN mv.vehiculo v
        JOIN ProgPreliminarDetalle d ON d.manifiestoVehiculo.id = mv.id
        JOIN d.tienda t
        LEFT JOIN RecepcionTienda r ON r.progPreliminarDetalle.id = d.id 
        WHERE mv.id = :manifiestoVehiculoId
        ORDER BY d.id
    """)
    List<DocumentationStopViewDTO> findRouteStopsByAssignmentId(Long manifiestoVehiculoId);
}