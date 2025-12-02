package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface ManifiestoVehiculoService extends GenericService<ManifiestoVehiculo, Long> {

    // --- Métodos DTO de presentación ---
    Optional<ManifiestoVehiculoDTO> getManifiestoVehiculoDTO(Long id);
    List<ManifiestoVehiculoDTO> getAllManifiestoVehiculos();

    // --- Método de Registro/Asignación ---
    ManifiestoVehiculoDTO register(ManifiestoVehiculoRegisterRequestDTO requestDTO);

    // --- Métodos de búsqueda especializados ---
    List<ManifiestoVehiculoDTO> findByManifiestoId(Long idManifiesto);
    List<ManifiestoVehiculoDTO> findByVehiculoId(Long idVehiculo);
}