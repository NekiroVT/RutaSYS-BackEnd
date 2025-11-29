package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManifiestoRepository extends JpaRepository<Manifiesto, Long> {
    Optional<Manifiesto> findByCodManifiesto(String codManifiesto);
}