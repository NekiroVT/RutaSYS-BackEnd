package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.dto.AyudanteRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface AyudanteMapper extends BaseMappers<Ayudante, AyudanteDTO> {

    // --- Mapeo Entidad (Ayudante) a DTO (AyudanteDTO) ---
    // Aplana el objeto 'usuario' en los campos 'usuarioId' y 'username' del DTO.
    @Override
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "username", source = "usuario.username")
    AyudanteDTO toDTO(Ayudante ayudante);

    // --- Mapeo DTO de Registro (AyudanteRegisterRequestDTO) a Entidad (Ayudante) ---
    // Usado en AuthService. Ignoramos los campos de Usuario y de control de la DB.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Ayudante toEntity(AyudanteRegisterRequestDTO requestDTO);

    // --- Mapeo DTO (AyudanteDTO) a Entidad (Ayudante) ---
    // MapStruct no puede rellenar el objeto Usuario a partir de los campos planos.
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    Ayudante toEntity(AyudanteDTO ayudanteDTO);
}