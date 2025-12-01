package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.UsuarioDTO;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMappers<Usuario, UsuarioDTO> {

    // ❗ YA NO EXISTE "rol" EN USUARIO → QUITAMOS EL MAPE0
    @Override
    UsuarioDTO toDTO(Usuario usuario);

    @Override
    Usuario toEntity(UsuarioDTO usuarioDTO);

    // Si usas update:
    @Override
    void updateEntity(UsuarioDTO dto, @MappingTarget Usuario entity);

    @Override
    List<UsuarioDTO> toDTOs(List<Usuario> list);

    @Override
    List<Usuario> toEntityList(List<UsuarioDTO> list);
}
