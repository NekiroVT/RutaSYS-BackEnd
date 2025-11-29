package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroLlegadaChoferRepository extends JpaRepository<RegistroLlegadaChofer, Long> {
    // Útil para obtener el registro de llegada de una asignación específica
    Optional<RegistroLlegadaChofer> findByManifiestoVehiculoId(Long manifiestoVehiculoId);
}