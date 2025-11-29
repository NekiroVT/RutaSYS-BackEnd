package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface AyudanteMapper extends BaseMappers<Ayudante, AyudanteDTO> {

    // ➡️ Mapeo Entidad a DTO (Aplanar Usuario)
    @Override
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "username", source = "usuario.username")
    AyudanteDTO toDTO(Ayudante ayudante);

    // ➡️ Mapeo DTO a Entidad (Ignorar Usuario, el servicio lo asigna)
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    Ayudante toEntity(AyudanteDTO ayudanteDTO);
}