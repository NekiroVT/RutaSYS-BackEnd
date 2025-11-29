package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.TiendaDTO;
import pe.edu.upeu.g35.rutasys.entity.Tienda;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TiendaMapper extends BaseMappers<Tienda, TiendaDTO> {
    // Hereda los métodos de mapeo de BaseMappers.
}