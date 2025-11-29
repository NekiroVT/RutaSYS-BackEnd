package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Tienda;
import pe.edu.upeu.g35.rutasys.dto.TiendaDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface TiendaService extends GenericService<Tienda, Long> {

    Optional<TiendaDTO> getTiendaDTO(Long id);

    List<TiendaDTO> getAllTiendas();

    Optional<TiendaDTO> findByNombreTiendaDTO(String nombreTienda);
}