package pe.edu.upeu.g35.rutasys.service.base;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    // Guardar una entidad
    T save(T entity);

    // Buscar una entidad por su ID
    Optional<T> findById(ID id);

    // Eliminar una entidad por su ID
    void delete(ID id);

    // Obtener todas las entidades
    List<T> findAll();
}
