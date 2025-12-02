package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseDTO;
import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.AlmacenBase;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface AlmacenBaseMapper extends BaseMappers<AlmacenBase, AlmacenBaseDTO> {

    // Conversión de Entidad a DTO de Presentación
    @Override
    AlmacenBaseDTO toDTO(AlmacenBase almacenBase);

    // Conversión de DTO de Presentación a Entidad
    @Override
    AlmacenBase toEntity(AlmacenBaseDTO almacenBaseDTO);

    // Conversión de DTO de Registro a Entidad (ignora ID)
    @Mapping(target = "id", ignore = true)
    AlmacenBase toEntity(AlmacenBaseRegisterRequestDTO requestDTO);
}