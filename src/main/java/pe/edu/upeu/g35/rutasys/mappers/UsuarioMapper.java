package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.UsuarioDTO;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMappers<Usuario, UsuarioDTO> {

    @Override
    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "rolNombre", source = "rol.nombre")
    UsuarioDTO toDTO(Usuario usuario);

    @Override
    @InheritInverseConfiguration
    @Mapping(target = "rol", ignore = true) // El servicio se encarga de asignar el objeto Rol completo
    @Mapping(target = "password", ignore = true) // Nunca se debe mapear la contraseña a menos que sea un DTO de creación
    Usuario toEntity(UsuarioDTO usuarioDTO);
}