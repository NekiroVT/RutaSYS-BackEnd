package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.RolDTO;
import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper extends BaseMappers<Rol, RolDTO> {
    // Los métodos toDTO y toEntity se heredan de BaseMappers y MapStruct los implementa automáticamente.
    // No hay mapeo complejo, ya que RolDTO es plano.
}