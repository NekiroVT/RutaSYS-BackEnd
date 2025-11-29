package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Long> {

    // ➡️ MÉTODOS HEREDADOS DE JPA:
    // save(), findById(), findAll(), deleteById(), etc. son automáticos.

    // ➡️ BÚSQUEDA PERSONALIZADA:
    // Es el método que el servicio específico necesita para el DNI.
    Optional<Chofer> findByDni(String dni);

    // Opcional: Buscar por número de licencia
    Optional<Chofer> findByLicencia(String licencia);
}