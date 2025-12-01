package pe.edu.upeu.g35.rutasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.g35.rutasys.entity.Administrador;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    /**
     * Busca un Administrador por el ID de su Usuario asociado.
     * Esto es útil para operaciones de seguridad o perfil.
     *
     * @param idUsuario El ID del usuario asociado al Administrador.
     * @return Un Optional que contiene el Administrador si se encuentra.
     */
    Optional<Administrador> findByUsuarioId(Long idUsuario);

    /**
     * Verifica si existe un Administrador asociado a un ID de Usuario.
     *
     * @param idUsuario El ID del usuario.
     * @return true si existe un Administrador con ese ID de Usuario.
     */
    boolean existsByUsuarioId(Long idUsuario);

    /**
     * Busca un Administrador por su número de DNI.
     *
     * @param dni El número de DNI del Administrador.
     * @return Un Optional que contiene el Administrador si se encuentra.
     */
    Optional<Administrador> findByDni(String dni);
}