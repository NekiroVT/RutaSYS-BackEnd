package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ProgPreliminarDetalleDTO;
import pe.edu.upeu.g35.rutasys.entity.ProgPreliminarDetalle;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface ProgPreliminarDetalleMapper extends BaseMappers<ProgPreliminarDetalle, ProgPreliminarDetalleDTO> {

    // ➡️ Mapeo Entidad a DTO (Aplanar ManifiestoVehiculo y Tienda)
    @Override
    @Mapping(target = "manifiestoVehiculoId", source = "manifiestoVehiculo.id")
    @Mapping(target = "tiendaId", source = "tienda.id")
    @Mapping(target = "nombreTienda", source = "tienda.nombreTienda")
    ProgPreliminarDetalleDTO toDTO(ProgPreliminarDetalle detalle);

    // ➡️ Mapeo DTO a Entidad (Ignorar las Entidades, el servicio las asigna)
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "manifiestoVehiculo", ignore = true)
    @Mapping(target = "tienda", ignore = true)
    ProgPreliminarDetalle toEntity(ProgPreliminarDetalleDTO detalleDTO);
}