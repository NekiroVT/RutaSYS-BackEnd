package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ChoferDTO;
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers; // Asumiendo que esta interfaz existe
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

// componentModel = "spring" permite inyectar el mapper con @Autowired
@Mapper(componentModel = "spring")
public interface ChoferMapper extends BaseMappers<Chofer, ChoferDTO> {

    // --- Mapeo Entidad (Chofer) a DTO (ChoferDTO) ---
    // Aplana el objeto 'usuario' en los campos 'usuarioId' y 'username' del DTO.
    @Override
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "username", source = "usuario.username")
    ChoferDTO toDTO(Chofer chofer);

    // --- Mapeo DTO (ChoferDTO) a Entidad (Chofer) ---
    // Usamos @InheritInverseConfiguration para mapear los campos planos inversamente.
    // IGNORAMOS el campo 'usuario' porque el Mapper no puede crear un objeto Usuario
    // complejo solo a partir de 'usuarioId'.
    // Esta relación es manejada por el ChoferServiceImpl (buscando el Usuario en la BD).
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    Chofer toEntity(ChoferDTO choferDTO);
}