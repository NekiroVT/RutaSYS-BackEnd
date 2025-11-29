package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import pe.edu.upeu.g35.rutasys.dto.RolUsuarioDTO;
import pe.edu.upeu.g35.rutasys.repository.RolUsuarioRepository;
import pe.edu.upeu.g35.rutasys.repository.RolRepository;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;
import pe.edu.upeu.g35.rutasys.mappers.RolUsuarioMapper;
import pe.edu.upeu.g35.rutasys.service.service.RolUsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional; // ⬅️ IMPORTACIÓN NECESARIA
import java.util.stream.Collectors;

@Service
public class RolUsuarioServiceImpl implements RolUsuarioService {

    private final RolUsuarioRepository rolUsuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolUsuarioMapper rolUsuarioMapper;

    public RolUsuarioServiceImpl(RolUsuarioRepository rolUsuarioRepository, RolRepository rolRepository, UsuarioRepository usuarioRepository, RolUsuarioMapper rolUsuarioMapper) {
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolUsuarioMapper = rolUsuarioMapper;
    }

    // --- MÉTODOS DE NEGOCIO ---

    // ⬅️ IMPLEMENTACIÓN DEL MÉTODO FALTANTE
    @Override
    @Transactional(readOnly = true)
    public Optional<RolUsuarioDTO> getRolUsuarioDTO(Long id) {
        // Busca la entidad por ID y usa el mapper para convertirla a DTO
        return rolUsuarioRepository.findById(id).map(rolUsuarioMapper::toDTO);
    }

    @Override
    @Transactional
    public RolUsuarioDTO assignRoleToUser(Long rolId, Long usuarioId) {
        if (rolUsuarioRepository.existsByRolIdAndUsuarioId(rolId, usuarioId)) {
            throw new IllegalArgumentException("El rol ya está asignado a este usuario.");
        }

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + rolId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));

        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setRol(rol);
        rolUsuario.setUsuario(usuario);

        RolUsuario saved = rolUsuarioRepository.save(rolUsuario);
        return rolUsuarioMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long rolId, Long usuarioId) {
        rolUsuarioRepository.findAll().stream()
                .filter(ru -> ru.getRol().getId().equals(rolId) && ru.getUsuario().getId().equals(usuarioId))
                .findFirst()
                .ifPresentOrElse(
                        rolUsuarioRepository::delete,
                        () -> { throw new IllegalArgumentException("Asignación de rol no encontrada."); }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolUsuarioDTO> getRolesByUserId(Long usuarioId) {
        return rolUsuarioRepository.findByUsuarioId(usuarioId).stream()
                .map(rolUsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS HEREDADOS (CRUD Genérico) ---

    @Override
    public RolUsuario save(RolUsuario rolUsuario) {
        return rolUsuarioRepository.save(rolUsuario);
    }

    @Override
    public Optional<RolUsuario> findById(Long id) {
        return rolUsuarioRepository.findById(id);
    }

    @Override
    public List<RolUsuario> findAll() {
        return rolUsuarioRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        rolUsuarioRepository.deleteById(id);
    }
}