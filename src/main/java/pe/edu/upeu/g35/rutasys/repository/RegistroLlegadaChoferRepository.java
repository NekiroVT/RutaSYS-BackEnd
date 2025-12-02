package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroLlegadaChoferRepository extends JpaRepository<RegistroLlegadaChofer, Long> {

    // ➡️ BÚSQUEDA PERSONALIZADA:

    /**
     * Busca un registro de llegada por el ID de la asignación ManifiestoVehiculo.
     */
    Optional<RegistroLlegadaChofer> findByManifiestoVehiculo_Id(Long idManifiestoVehiculo);

    /**
     * Busca todos los registros de llegada realizados por un Chofer específico.
     */
    List<RegistroLlegadaChofer> findByChofer_Id(Long idChofer);

    /**
     * 🚀 MÉTODO CLAVE: Busca registros por ID de Vehículo y Estado.
     * Útil para la asistencia masiva.
     */
    @Query("SELECT rlc FROM RegistroLlegadaChofer rlc " +
            "WHERE rlc.manifiestoVehiculo.vehiculo.id = :idVehiculo AND rlc.estadoLlegada = :estado")
    List<RegistroLlegadaChofer> findByVehiculoIdAndEstado(Long idVehiculo, String estado);

    /**
     * Búsqueda de todos los registros de llegada asociados a un Vehículo (para informes).
     */
    @Query("SELECT rlc FROM RegistroLlegadaChofer rlc WHERE rlc.manifiestoVehiculo.vehiculo.id = :idVehiculo")
    List<RegistroLlegadaChofer> findByVehiculoId(Long idVehiculo);
}