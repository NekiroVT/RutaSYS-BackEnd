package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.ProgPreliminarDetalle;
import pe.edu.upeu.g35.rutasys.dto.ProgPreliminarDetalleDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;
import pe.edu.upeu.g35.rutasys.dto.InfoStopViewDTO; // ⬅️ DTO ACTUALIZADO

import java.util.List;
import java.util.Optional;

public interface ProgPreliminarDetalleService extends GenericService<ProgPreliminarDetalle, Long> {

    ProgPreliminarDetalleDTO saveDetalle(ProgPreliminarDetalle detalle);

    Optional<ProgPreliminarDetalleDTO> getDetalleDTO(Long id);

    List<ProgPreliminarDetalleDTO> getDetallesByManifiestoVehiculoId(Long manifiestoVehiculoId);

    Optional<InfoStopViewDTO> getStopDetailViewById(Long detalleId); // ⬅️ MÉTODO ACTUALIZADO
}