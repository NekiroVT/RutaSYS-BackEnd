package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AyudanteRepository extends JpaRepository<Ayudante, Long> {

    /**
     * Busca un ayudante por su número de DNI.
     */
    Optional<Ayudante> findByDni(String dni);

    /**
     * Busca el ayudante asociado a un ID de Usuario específico.
     */
    Optional<Ayudante> findByUsuario_Id(Long usuarioId);
}