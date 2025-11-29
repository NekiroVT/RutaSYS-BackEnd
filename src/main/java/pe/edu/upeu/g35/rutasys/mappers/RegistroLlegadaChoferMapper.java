package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface RegistroLlegadaChoferMapper extends BaseMappers<RegistroLlegadaChofer, RegistroLlegadaChoferDTO> {

    // ➡️ Mapeo Entidad a DTO (Añadimos el aplanamiento del nombre)
    @Override
    @Mapping(target = "manifiestoVehiculoId", source = "manifiestoVehiculo.id")
    @Mapping(target = "choferId", source = "chofer.id")
    @Mapping(target = "nombreChofer", source = "chofer.nombreCompleto") // ⬅️ NUEVO MAPEO
    RegistroLlegadaChoferDTO toDTO(RegistroLlegadaChofer registro);

    // ➡️ Mapeo DTO a Entidad (No necesita cambios en la lógica de ignorar)
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "manifiestoVehiculo", ignore = true)
    @Mapping(target = "chofer", ignore = true)
    RegistroLlegadaChofer toEntity(RegistroLlegadaChoferDTO registroDTO);
}