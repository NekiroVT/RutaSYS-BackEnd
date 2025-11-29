package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculoMapper extends BaseMappers<Vehiculo, VehiculoDTO> {
    // Mapeo automático de Vehiculo a VehiculoDTO y viceversa
}