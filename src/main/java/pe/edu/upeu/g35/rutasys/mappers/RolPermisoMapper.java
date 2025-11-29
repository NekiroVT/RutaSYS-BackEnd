package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.RolPermisoDTO;
import pe.edu.upeu.g35.rutasys.entity.RolPermiso;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolPermisoMapper extends BaseMappers<RolPermiso, RolPermisoDTO> {

    @Override
    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "permisoId", source = "permiso.id")
    RolPermisoDTO toDTO(RolPermiso rolPermiso);

    @Override
    @Mapping(target = "rol", ignore = true)     // El servicio busca la entidad Rol
    @Mapping(target = "permiso", ignore = true) // El servicio busca la entidad Permiso
    RolPermiso toEntity(RolPermisoDTO rolPermisoDTO);
}