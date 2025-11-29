package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface ManifiestoVehiculoService extends GenericService<ManifiestoVehiculo, Long> {

    // Método principal para asignar recursos, manejando la validación de dependencias
    ManifiestoVehiculoDTO assignResourcesToManifest(ManifiestoVehiculoDTO assignmentDTO);

    // Lista de asignaciones por manifiesto (para la vista de la ruta)
    List<ManifiestoVehiculoDTO> getAssignmentsByManifestId(Long manifiestoId);

    Optional<ManifiestoVehiculoDTO> getManifiestoVehiculoDTO(Long id);
}