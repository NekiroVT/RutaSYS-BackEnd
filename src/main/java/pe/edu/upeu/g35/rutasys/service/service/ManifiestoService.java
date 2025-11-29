package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface ManifiestoService extends GenericService<Manifiesto, Long> {

    Optional<ManifiestoDTO> getManifiestoDTO(Long id);

    List<ManifiestoDTO> getAllManifiestos();

    ManifiestoDTO saveManifiesto(Manifiesto manifiesto);
}