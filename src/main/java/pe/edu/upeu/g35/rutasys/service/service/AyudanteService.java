package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

// Extiende el servicio genérico para heredar save, findById, delete y findAll.
public interface AyudanteService extends GenericService<Ayudante, Long> {

    // 1. Método para registrar un ayudante (Lógica de negocio especializada/DTO)
    AyudanteDTO registerAyudante(Ayudante ayudante);

    // 2. Método para obtener un ayudante por ID (Retornando DTO)
    Optional<AyudanteDTO> getAyudanteDTO(Long id);

    // 3. Método para obtener todos los ayudantes (Retornando lista de DTOs)
    List<AyudanteDTO> getAllAyudantes();

    // 4. Búsqueda especializada por DNI
    Optional<AyudanteDTO> findByDniDTO(String dni);
}