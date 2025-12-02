package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.AlmacenBase;
import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseDTO;
import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface AlmacenBaseService extends GenericService<AlmacenBase, Long> {

    // --- Métodos DTO de presentación/lectura ---
    Optional<AlmacenBaseDTO> getAlmacenBaseDTO(Long id);
    List<AlmacenBaseDTO> getAllAlmacenesBase();

    // --- Métodos de escritura (CRUD con DTOs) ---
    AlmacenBaseDTO register(AlmacenBaseRegisterRequestDTO requestDTO);
    AlmacenBaseDTO update(Long id, AlmacenBaseDTO updateDTO);

    // --- Métodos de búsqueda especializados ---
    Optional<AlmacenBaseDTO> findByNombreDTO(String nombre);
}