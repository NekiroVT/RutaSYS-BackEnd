package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.PermisoDTO;
import pe.edu.upeu.g35.rutasys.entity.Permiso;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermisoMapper extends BaseMappers<Permiso, PermisoDTO> {
    // Hereda los métodos de CRUD de mapeo.
}