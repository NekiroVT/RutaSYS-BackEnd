package pe.edu.upeu.g35.rutasys.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upeu.g35.rutasys.dto.AdministradorDTO;
import pe.edu.upeu.g35.rutasys.entity.Administrador;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.mappers.AdministradorMapper;
import pe.edu.upeu.g35.rutasys.repository.AdministradorRepository;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;

import pe.edu.upeu.g35.rutasys.service.AdministradorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper administradorMapper;
    private final UsuarioRepository usuarioRepository;

    private static final String ENTITY_NAME = "Administrador";
    private static final String ESTADO_ACTIVO = "ACTIVO";

    // Inyección explícita por constructor
    public AdministradorServiceImpl(AdministradorRepository administradorRepository,
                                    AdministradorMapper administradorMapper,
                                    UsuarioRepository usuarioRepository) {
        this.administradorRepository = administradorRepository;
        this.administradorMapper = administradorMapper;
        this.usuarioRepository = usuarioRepository;
    }

    // ---------------------- Métodos HEREDADOS (GenericService<Administrador, Long>) ----------------------

    @Override
    @Transactional
    public Administrador save(Administrador entity) {
        // Método genérico, guarda la entidad tal como se recibe.
        return administradorRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!administradorRepository.existsById(id)) {
            // Usando RuntimeException o similar
            throw new RuntimeException(ENTITY_NAME + " no encontrado con ID: " + id);
        }
        administradorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    // ---------------------- Métodos ESPECÍFICOS (DTO) ----------------------

    @Override
    @Transactional
    public AdministradorDTO saveAdministrador(Administrador administrador) {
        if (administrador.getId() == null) {
            return createAdministrador(administrador);
        } else {
            return updateAdministrador(administrador);
        }
    }

    private AdministradorDTO createAdministrador(Administrador administrador) {
        // 1. Validar DNI único antes de crear
        if (administradorRepository.findByDni(administrador.getDni()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado para otro administrador.");
        }

        // 2. Buscar y validar Usuario (la lógica de registro del AuthService garantiza que el usuario existe)
        // Se asume que el objeto 'usuario' dentro de 'administrador' ya tiene el ID.
        Long usuarioId = administrador.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        // 3. Verificar que el usuario no esté asociado ya a otro administrador
        if (administradorRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Este usuario ya está asociado a un Administrador.");
        }

        // 4. Establecer relaciones y valores por defecto
        administrador.setUsuario(usuario);
        // Si el estado no viene definido, lo pone ACTIVO
        if (administrador.getEstado() == null || administrador.getEstado().isEmpty()) {
            administrador.setEstado(ESTADO_ACTIVO);
        }

        // 5. Guardar y Mapear a DTO
        Administrador savedAdmin = administradorRepository.save(administrador);
        return administradorMapper.toDTO(savedAdmin);
    }

    private AdministradorDTO updateAdministrador(Administrador administrador) {
        Administrador existingAdmin = administradorRepository.findById(administrador.getId())
                .orElseThrow(() -> new RuntimeException(ENTITY_NAME + " no encontrado con ID: " + administrador.getId()));

        // 1. Validar DNI (si el DNI es nuevo y ya está en uso por otro ID)
        administradorRepository.findByDni(administrador.getDni())
                .ifPresent(admin -> {
                    if (!admin.getId().equals(administrador.getId())) {
                        throw new RuntimeException("El DNI ya está registrado para otro administrador.");
                    }
                });

        // 2. Mapear/Actualizar los campos de la entidad existente
        existingAdmin.setNombreCompleto(administrador.getNombreCompleto());
        existingAdmin.setDni(administrador.getDni());
        existingAdmin.setTelefono(administrador.getTelefono());
        existingAdmin.setCargo(administrador.getCargo());
        existingAdmin.setEstado(administrador.getEstado());

        // La relación Usuario (existingAdmin.getUsuario()) no se debe cambiar en una actualización de perfil,
        // ya que la cuenta de usuario es fija.

        // 3. Guardar y Mapear a DTO
        Administrador updatedAdmin = administradorRepository.save(existingAdmin);
        return administradorMapper.toDTO(updatedAdmin);
    }

    // ---------------------- Métodos de Búsqueda de DTO ----------------------

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministradorDTO> getAdministradorDTO(Long id) {
        return administradorRepository.findById(id)
                .map(administradorMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdministradorDTO> getAllAdministradores() {
        return administradorRepository.findAll().stream()
                .map(administradorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministradorDTO> findByDniDTO(String dni) {
        return administradorRepository.findByDni(dni)
                .map(administradorMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministradorDTO> findByUsuarioIdDTO(Long usuarioId) {
        return administradorRepository.findByUsuarioId(usuarioId)
                .map(administradorMapper::toDTO);
    }
}