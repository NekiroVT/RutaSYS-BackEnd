package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.entity.*;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface ManifiestoVehiculoMapper extends BaseMappers<ManifiestoVehiculo, ManifiestoVehiculoDTO> {

    // ➡️ Mapeo Entidad a DTO (Aplanar todas las FKs)
    @Override
    @Mapping(target = "manifiestoId", source = "manifiesto.id")
    @Mapping(target = "vehiculoId", source = "vehiculo.id")
    @Mapping(target = "choferId", source = "chofer.id")
    @Mapping(target = "ayudanteId", source = "ayudante.id")
    ManifiestoVehiculoDTO toDTO(ManifiestoVehiculo manifiestoVehiculo);

    // ➡️ Mapeo DTO a Entidad (Ignorar las Entidades, el servicio las asigna)
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "manifiesto", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    @Mapping(target = "chofer", ignore = true)
    @Mapping(target = "ayudante", ignore = true)
    ManifiestoVehiculo toEntity(ManifiestoVehiculoDTO manifiestoVehiculoDTO);
}