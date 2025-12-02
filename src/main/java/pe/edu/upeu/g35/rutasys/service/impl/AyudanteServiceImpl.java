package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.repository.AyudanteRepository;
import pe.edu.upeu.g35.rutasys.mappers.AyudanteMapper;
import pe.edu.upeu.g35.rutasys.service.service.AyudanteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AyudanteServiceImpl implements AyudanteService {

    private final AyudanteRepository ayudanteRepository;
    private final AyudanteMapper ayudanteMapper;

    public AyudanteServiceImpl(AyudanteRepository ayudanteRepository, AyudanteMapper ayudanteMapper) {
        this.ayudanteRepository = ayudanteRepository;
        this.ayudanteMapper = ayudanteMapper;
    }

    // --- MÉTODOS HEREDADOS DE GenericService (CRUD de Entidad) ---

    @Override
    @Transactional
    public Ayudante save(Ayudante ayudante) {
        return ayudanteRepository.save(ayudante);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ayudante> findById(Long id) { return ayudanteRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Ayudante> findAll() { return ayudanteRepository.findAll(); }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!ayudanteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Ayudante con ID " + id + " no encontrado.");
        }
        ayudanteRepository.deleteById(id);
    }

    // --- MÉTODOS DTO de Presentación y Búsqueda Básica ---

    @Override
    @Transactional(readOnly = true)
    public Optional<AyudanteDTO> getAyudanteDTO(Long id) {
        return ayudanteRepository.findById(id).map(ayudanteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AyudanteDTO> getAllAyudantes() {
        return ayudanteRepository.findAll().stream().map(ayudanteMapper::toDTO).collect(Collectors.toList());
    }

    // --- 1. MÉTODO DE REGISTRO (Lógica de negocio especializada) ---

    @Override
    @Transactional
    public AyudanteDTO registerAyudante(Ayudante ayudante) {
        // Se asume que la entidad Ayudante ya fue validada y su estado fue establecido
        // por la capa de seguridad (AuthService) antes de llegar aquí.

        Ayudante savedAyudante = ayudanteRepository.save(ayudante);

        // Mapear la entidad guardada de vuelta al DTO para la respuesta
        return ayudanteMapper.toDTO(savedAyudante);
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES ---

    @Override
    @Transactional(readOnly = true)
    public Optional<AyudanteDTO> findByDniDTO(String dni) {
        return ayudanteRepository.findByDni(dni).map(ayudanteMapper::toDTO);
    }
}