package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.RolUsuarioDTO;
import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolUsuarioMapper extends BaseMappers<RolUsuario, RolUsuarioDTO> {

    @Override
    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    RolUsuarioDTO toDTO(RolUsuario rolUsuario);

    @Override
    @Mapping(target = "rol", ignore = true) // El servicio se encarga de buscar el Rol por ID
    @Mapping(target = "usuario", ignore = true) // El servicio se encarga de buscar el Usuario por ID
    RolUsuario toEntity(RolUsuarioDTO rolUsuarioDTO);
}