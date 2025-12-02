package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface ManifiestoService extends GenericService<Manifiesto, Long> {

    // --- Métodos DTO de presentación ---
    Optional<ManifiestoDTO> getManifiestoDTO(Long id);
    List<ManifiestoDTO> getAllManifiestos();

    // --- Método de Registro (Lógica de negocio especializada) ---
    ManifiestoDTO register(ManifiestoRegisterRequestDTO requestDTO);

    // --- Métodos de búsqueda especializados ---
    Optional<ManifiestoDTO> findByCodManifiestoDTO(String codManifiesto);
    List<ManifiestoDTO> findByClienteIdDTO(Long idCliente);
}