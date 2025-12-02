package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ClienteDTO;
import pe.edu.upeu.g35.rutasys.dto.ClienteRegisterRequestDTO; // Necesario para el registro
import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
// 🟢 Usamos ClienteDTO (Presentación) para la herencia básica de BaseMappers
public interface ClienteMapper extends BaseMappers<Cliente, ClienteDTO> {

    // Este toDTO() hereda de BaseMappers y genera Cliente -> ClienteDTO
    // Este toEntity() hereda de BaseMappers y genera ClienteDTO -> Cliente

    // --- Mapeo Específico para el DTO de REGISTRO ---

    /**
     * Mapeo especial para convertir el DTO de Registro a la Entidad.
     * Es necesario porque el DTO de registro es diferente al DTO de presentación.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cliente toEntity(ClienteRegisterRequestDTO requestDTO); // Usa el DTO de REGISTRO

    // NOTA: MapStruct generará automáticamente toDTO, updateEntity, toDTOs, y toEntityList
}