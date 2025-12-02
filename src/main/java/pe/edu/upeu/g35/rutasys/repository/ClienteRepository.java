package pe.edu.upeu.g35.rutasys.repository;

import pe.edu.upeu.g35.rutasys.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // ➡️ MÉTODOS HEREDADOS DE JPA:
    // save(), findById(), findAll(), deleteById(), etc. son automáticos.

    // ➡️ BÚSQUEDA PERSONALIZADA:

    /**
     * Busca un cliente por su RUC (Registro Único de Contribuyentes).
     * @param ruc El RUC del cliente.
     * @return Un Optional que contiene el Cliente si es encontrado.
     */
    Optional<Cliente> findByRuc(String ruc);

    /**
     * Busca clientes cuya Razón Social contenga la cadena proporcionada,
     * útil para autocompletado o filtros.
     * @param razonSocial La cadena de búsqueda de la razón social.
     * @return Una lista de Clientes que coinciden parcialmente.
     */
    List<Cliente> findByRazonSocialContainingIgnoreCase(String razonSocial);

    /**
     * Busca todos los clientes por un estado específico (ej: "ACTIVO", "INACTIVO").
     * @param estado El estado del cliente.
     * @return Una lista de Clientes con el estado proporcionado.
     */
    List<Cliente> findByEstado(String estado);
}