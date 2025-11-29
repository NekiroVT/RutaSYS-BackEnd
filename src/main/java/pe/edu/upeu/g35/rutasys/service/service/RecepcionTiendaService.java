package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.RecepcionTienda;
import pe.edu.upeu.g35.rutasys.dto.RecepcionTiendaDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface RecepcionTiendaService extends GenericService<RecepcionTienda, Long> {

    // Método para manejar la lógica de dependencia y actualización del estado de ProgPreliminarDetalle
    RecepcionTiendaDTO registrarRecepcion(RecepcionTienda recepcionTienda);

    Optional<RecepcionTiendaDTO> getRecepcionDTO(Long id);

    Optional<RecepcionTiendaDTO> getRecepcionByDetalleId(Long progPreliminarDetalleId);
}