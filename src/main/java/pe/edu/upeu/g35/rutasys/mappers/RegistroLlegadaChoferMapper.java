package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface RegistroLlegadaChoferMapper extends BaseMappers<RegistroLlegadaChofer, RegistroLlegadaChoferDTO> {

    // --- Mapeo Entidad a DTO de Presentación ---
    @Override
    @Mapping(target = "manifiestoVehiculoId", source = "manifiestoVehiculo.id")
    @Mapping(target = "manifiestoVehiculoCod", source = "manifiestoVehiculo.manifiesto.codManifiesto") // Asumo que puedes acceder al código del manifiesto
    @Mapping(target = "choferId", source = "chofer.id")
    @Mapping(target = "choferNombreCompleto", source = "chofer.nombreCompleto")
    RegistroLlegadaChoferDTO toDTO(RegistroLlegadaChofer registro);

    // --- Mapeo DTO de Registro a Entidad ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaHoraLlegada", ignore = true) // Se fija en el servicio
    @Mapping(target = "estadoLlegada", ignore = true) // Se fija en el servicio
    @Mapping(target = "manifiestoVehiculo", ignore = true) // Servicio busca entidad
    @Mapping(target = "chofer", ignore = true) // Servicio busca entidad
    RegistroLlegadaChofer toEntity(RegistroLlegadaChoferRegisterRequestDTO requestDTO);

    // --- Mapeo DTO de Presentación a Entidad ---
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "manifiestoVehiculo", ignore = true)
    @Mapping(target = "chofer", ignore = true)
    RegistroLlegadaChofer toEntity(RegistroLlegadaChoferDTO dto);
}