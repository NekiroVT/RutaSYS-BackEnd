package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.AdministradorDTO;
import pe.edu.upeu.g35.rutasys.entity.Administrador;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

// ComponentModel 'spring' para que Spring pueda inyectarlo automáticamente.
@Mapper(componentModel = "spring")
public interface AdministradorMapper extends BaseMappers<Administrador, AdministradorDTO> {

    // ➡️ Mapeo Entidad a DTO (E -> DTO)
    // Aplanar la relación Usuario: extrae solo el ID (el username fue eliminado por solicitud).
    @Override
    @Mapping(target = "usuarioId", source = "usuario.id")
    // Se elimina: @Mapping(target = "username", source = "usuario.username")
    AdministradorDTO toDTO(Administrador administrador);

    // ➡️ Mapeo DTO a Entidad (DTO -> E)
    // Ignorar el campo 'usuario' en la entidad; el servicio lo asigna.
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    Administrador toEntity(AdministradorDTO administradorDTO);
}