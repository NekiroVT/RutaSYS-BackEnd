package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.entity.Permiso;
import pe.edu.upeu.g35.rutasys.entity.RolPermiso;
import pe.edu.upeu.g35.rutasys.dto.RolPermisoDTO;
import pe.edu.upeu.g35.rutasys.repository.RolPermisoRepository;
import pe.edu.upeu.g35.rutasys.repository.RolRepository;
import pe.edu.upeu.g35.rutasys.repository.PermisoRepository;
import pe.edu.upeu.g35.rutasys.mappers.RolPermisoMapper;
import pe.edu.upeu.g35.rutasys.service.service.RolPermisoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final RolPermisoMapper rolPermisoMapper;

    public RolPermisoServiceImpl(RolPermisoRepository rolPermisoRepository, RolRepository rolRepository, PermisoRepository permisoRepository, RolPermisoMapper rolPermisoMapper) {
        this.rolPermisoRepository = rolPermisoRepository;
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
        this.rolPermisoMapper = rolPermisoMapper;
    }

    // --- MÉTODOS DE NEGOCIO ---

    @Override
    @Transactional
    public RolPermisoDTO assignPermissionToRole(Long rolId, Long permisoId) {
        if (rolPermisoRepository.existsByRolIdAndPermisoId(rolId, permisoId)) {
            throw new IllegalArgumentException("El permiso ya está asignado a este rol.");
        }

        // 1. Buscar entidades (Validación de existencia)
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + rolId));
        Permiso permiso = permisoRepository.findById(permisoId)
                .orElseThrow(() -> new IllegalArgumentException("Permiso no encontrado con ID: " + permisoId));

        // 2. Crear la relación
        RolPermiso rolPermiso = new RolPermiso();
        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);

        // 3. Guardar y mapear a DTO
        RolPermiso saved = rolPermisoRepository.save(rolPermiso);
        return rolPermisoMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void removePermissionFromRole(Long rolId, Long permisoId) {
        // Buscar la entidad de relación por los dos IDs y eliminarla
        rolPermisoRepository.findAll().stream()
                .filter(rp -> rp.getRol().getId().equals(rolId) && rp.getPermiso().getId().equals(permisoId))
                .findFirst()
                .ifPresentOrElse(
                        rolPermisoRepository::delete,
                        () -> { throw new IllegalArgumentException("Asignación de permiso no encontrada."); }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolPermisoDTO> getPermissionsByRoleId(Long rolId) {
        return rolPermisoRepository.findByRolId(rolId).stream()
                .map(rolPermisoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS DE LECTURA DTO ---

    @Override
    @Transactional(readOnly = true)
    public Optional<RolPermisoDTO> getRolPermisoDTO(Long id) {
        return rolPermisoRepository.findById(id).map(rolPermisoMapper::toDTO);
    }

    // --- MÉTODOS HEREDADOS (CRUD Genérico) ---

    @Override
    public RolPermiso save(RolPermiso rolPermiso) {
        return rolPermisoRepository.save(rolPermiso);
    }

    @Override
    public Optional<RolPermiso> findById(Long id) {
        return rolPermisoRepository.findById(id);
    }

    @Override
    public List<RolPermiso> findAll() {
        return rolPermisoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        rolPermisoRepository.deleteById(id);
    }
}