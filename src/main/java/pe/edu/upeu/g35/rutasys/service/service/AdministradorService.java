package pe.edu.upeu.g35.rutasys.service;

import pe.edu.upeu.g35.rutasys.entity.Administrador; // Se usa la Entidad para el servicio genérico
import pe.edu.upeu.g35.rutasys.dto.AdministradorDTO; // Se usa el DTO para la comunicación externa
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio específico para la gestión de Administradores.
 * Extiende GenericService usando la Entidad (Administrador) y Long como tipo de ID.
 */
public interface AdministradorService extends GenericService<Administrador, Long> {

    // Métodos que devuelven DTOs para la capa de presentación.
    Optional<AdministradorDTO> getAdministradorDTO(Long id);

    List<AdministradorDTO> getAllAdministradores();

    Optional<AdministradorDTO> findByDniDTO(String dni);

    Optional<AdministradorDTO> findByUsuarioIdDTO(Long usuarioId);

    /**
     * Guarda o actualiza un Administrador. Recibe la Entidad y devuelve el DTO.
     * @param administrador La entidad Administrador a guardar.
     * @return El DTO del administrador guardado.
     */
    AdministradorDTO saveAdministrador(Administrador administrador);
}