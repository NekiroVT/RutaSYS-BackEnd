package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Permiso;
import pe.edu.upeu.g35.rutasys.dto.PermisoDTO;
import pe.edu.upeu.g35.rutasys.repository.PermisoRepository;
import pe.edu.upeu.g35.rutasys.mappers.PermisoMapper;
import pe.edu.upeu.g35.rutasys.service.service.PermisoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;
    private final PermisoMapper permisoMapper;

    public PermisoServiceImpl(PermisoRepository permisoRepository, PermisoMapper permisoMapper) {
        this.permisoRepository = permisoRepository;
        this.permisoMapper = permisoMapper;
    }

    // --- MÉTODOS HEREDADOS (GenericService) ---

    @Override
    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public Optional<Permiso> findById(Long id) {
        return permisoRepository.findById(id);
    }

    @Override
    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!permisoRepository.existsById(id)) {
            throw new RuntimeException("Permiso con ID " + id + " no encontrado.");
        }
        permisoRepository.deleteById(id);
    }

    // --- MÉTODOS ESPECÍFICOS (DTO) ---

    @Override
    public Optional<PermisoDTO> getPermisoDTO(Long id) {
        return permisoRepository.findById(id).map(permisoMapper::toDTO);
    }

    @Override
    public List<PermisoDTO> getAllPermisos() {
        return permisoRepository.findAll().stream()
                .map(permisoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermisoDTO> findByNombreDTO(String nombre) {
        return permisoRepository.findByNombre(nombre).map(permisoMapper::toDTO);
    }
}