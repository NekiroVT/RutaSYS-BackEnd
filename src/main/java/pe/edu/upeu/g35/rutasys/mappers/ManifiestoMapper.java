package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

// Nota: Aquí se asume que ClienteMapper está disponible para inyección, si lo necesitas en el futuro.
@Mapper(componentModel = "spring")
public interface ManifiestoMapper extends BaseMappers<Manifiesto, ManifiestoDTO> {

    // --- Mapeo Entidad (Manifiesto) a DTO (ManifiestoDTO) ---
    // Aplanamos la información del cliente en el DTO de presentación
    @Override
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "clienteRuc", source = "cliente.ruc")
    @Mapping(target = "clienteRazonSocial", source = "cliente.razonSocial")
    ManifiestoDTO toDTO(Manifiesto manifiesto);

    // --- Mapeo DTO de Registro (ManifiestoRegisterRequestDTO) a Entidad (Manifiesto) ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true) // El estado se fija en el servicio
    @Mapping(target = "fechaSolicitud", ignore = true) // La fecha de solicitud se fija en el servicio
    @Mapping(target = "cliente", ignore = true) // El servicio debe establecer la entidad Cliente completa
    Manifiesto toEntity(ManifiestoRegisterRequestDTO requestDTO);

    // --- Mapeo DTO (ManifiestoDTO) a Entidad (Manifiesto) ---
    // Inverso al toDTO, ignoramos el objeto cliente ya que el mapper no puede crearlo.
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true)
    Manifiesto toEntity(ManifiestoDTO manifiestoDTO);
}