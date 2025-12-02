package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface ManifiestoVehiculoMapper extends BaseMappers<ManifiestoVehiculo, ManifiestoVehiculoDTO> {

    // --- Mapeo Entidad (ManifiestoVehiculo) a DTO (ManifiestoVehiculoDTO) ---
    // Aplanamos las 4 entidades relacionadas
    @Override
    @Mapping(target = "manifiestoId", source = "manifiesto.id")
    @Mapping(target = "manifiestoCod", source = "manifiesto.codManifiesto")
    @Mapping(target = "vehiculoId", source = "vehiculo.id")
    @Mapping(target = "vehiculoPlaca", source = "vehiculo.placa")
    @Mapping(target = "choferId", source = "chofer.id")
    @Mapping(target = "choferNombreCompleto", source = "chofer.nombreCompleto")
    @Mapping(target = "ayudanteId", source = "ayudante.id")
    @Mapping(target = "ayudanteNombreCompleto", source = "ayudante.nombreCompleto")
    ManifiestoVehiculoDTO toDTO(ManifiestoVehiculo manifiestoVehiculo);

    // --- Mapeo DTO de Registro (Request) a Entidad (ManifiestoVehiculo) ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true) // El servicio fija el estado
    // Ignoramos todas las entidades, el servicio las buscará por ID
    @Mapping(target = "manifiesto", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    @Mapping(target = "chofer", ignore = true)
    @Mapping(target = "ayudante", ignore = true)
    ManifiestoVehiculo toEntity(ManifiestoVehiculoRegisterRequestDTO requestDTO);

    // --- Mapeo DTO (ManifiestoVehiculoDTO) a Entidad (ManifiestoVehiculo) ---
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "manifiesto", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    @Mapping(target = "chofer", ignore = true)
    @Mapping(target = "ayudante", ignore = true)
    ManifiestoVehiculo toEntity(ManifiestoVehiculoDTO manifiestoVehiculoDTO);
}