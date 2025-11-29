package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.dto.RolDTO;
import pe.edu.upeu.g35.rutasys.repository.RolRepository;
import pe.edu.upeu.g35.rutasys.mappers.RolMapper;
import pe.edu.upeu.g35.rutasys.service.service.RolService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolServiceImpl(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    // --- MÉTODOS HEREDADOS (Implementan GenericService) ---

    @Override
    public Rol save(Rol rol) {
        // Lógica: Se puede verificar unicidad del nombre antes de guardar
        return rolRepository.save(rol);
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }

    // --- MÉTODOS ESPECÍFICOS DE RolService (Usan DTO) ---

    @Override
    public Optional<RolDTO> getRolDTO(Long id) {
        return rolRepository.findById(id).map(rolMapper::toDTO);
    }

    @Override
    public List<RolDTO> getAllRoles() {
        return rolRepository.findAll().stream()
                .map(rolMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
}