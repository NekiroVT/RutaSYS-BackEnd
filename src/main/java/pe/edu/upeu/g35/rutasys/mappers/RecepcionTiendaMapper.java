package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.RecepcionTiendaDTO;
import pe.edu.upeu.g35.rutasys.entity.RecepcionTienda;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface RecepcionTiendaMapper extends BaseMappers<RecepcionTienda, RecepcionTiendaDTO> {

    // ➡️ Mapeo Entidad a DTO (Aplanar ProgPreliminarDetalle)
    @Override
    @Mapping(target = "progPreliminarDetalleId", source = "progPreliminarDetalle.id")
    RecepcionTiendaDTO toDTO(RecepcionTienda recepcionTienda);

    // ➡️ Mapeo DTO a Entidad (Ignorar la Entidad, el servicio la asigna)
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "progPreliminarDetalle", ignore = true)
    RecepcionTienda toEntity(RecepcionTiendaDTO recepcionTiendaDTO);
}