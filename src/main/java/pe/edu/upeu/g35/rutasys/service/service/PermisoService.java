package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Permiso;
import pe.edu.upeu.g35.rutasys.dto.PermisoDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface PermisoService extends GenericService<Permiso, Long> {

    // Métodos específicos (usando DTOs)
    Optional<PermisoDTO> getPermisoDTO(Long id);

    List<PermisoDTO> getAllPermisos();

    Optional<PermisoDTO> findByNombreDTO(String nombre);
}