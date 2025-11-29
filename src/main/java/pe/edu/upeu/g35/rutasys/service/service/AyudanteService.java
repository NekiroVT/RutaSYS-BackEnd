package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface AyudanteService extends GenericService<Ayudante, Long> {

    Optional<AyudanteDTO> getAyudanteDTO(Long id);

    List<AyudanteDTO> getAllAyudantes();

    Optional<AyudanteDTO> findByDniDTO(String dni);

    AyudanteDTO saveAyudante(Ayudante ayudante); // Maneja la lógica de dependencia del Usuario
}