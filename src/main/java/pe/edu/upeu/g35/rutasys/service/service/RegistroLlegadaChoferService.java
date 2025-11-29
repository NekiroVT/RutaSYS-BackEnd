package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.Optional;
import java.util.List;

public interface RegistroLlegadaChoferService extends GenericService<RegistroLlegadaChofer, Long> {

    // Método para manejar la lógica de dependencia y el registro
    RegistroLlegadaChoferDTO registrarLlegada(RegistroLlegadaChofer registro);

    Optional<RegistroLlegadaChoferDTO> getRegistroDTO(Long id);

    Optional<RegistroLlegadaChoferDTO> getRegistroByManifiestoVehiculoId(Long manifiestoVehiculoId);
}