package pe.edu.upeu.g35.rutasys.mappers.base;

import java.util.List;
import org.mapstruct.MappingTarget; // ⬅️ NECESARIO para MapStruct

public interface BaseMappers <E,DTO>{

    // ... (Métodos que ya tienes)

    // Método para actualizar una entidad existente a partir de un DTO
    void updateEntity(DTO dto, @MappingTarget E entity); // ⬅️ NUEVO MÉTODO SUGERIDO

    DTO toDTO(E entity);
    E toEntity(DTO dto);
    List<DTO> toDTOs(List<E> list);
    List<E> toEntityList(List<DTO> list);
}