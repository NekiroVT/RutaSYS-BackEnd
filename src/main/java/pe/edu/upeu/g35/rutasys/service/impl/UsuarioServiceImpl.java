package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.dto.UsuarioDTO;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;
import pe.edu.upeu.g35.rutasys.mappers.UsuarioMapper;
import pe.edu.upeu.g35.rutasys.service.service.UsuarioService;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    // ----------------------------------------------------------------------
    // MÉTODOS HEREDADOS DE GenericService (CRUD BÁSICO)
    // ----------------------------------------------------------------------

    @Override
    public Usuario save(Usuario usuario) {
        // NOTA: Aquí solo se actualiza o guarda, la encriptación debe hacerse antes
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

    // ----------------------------------------------------------------------
    // MÉTODOS ESPECÍFICOS DE UsuarioService (DTO y Búsqueda)
    // ----------------------------------------------------------------------

    @Override
    public Optional<UsuarioDTO> getUsuarioDTO(Long id) {
        // Busca la entidad y usa el mapper para convertir a DTO
        return usuarioRepository.findById(id).map(usuarioMapper::toDTO);
    }

    @Override
    public List<UsuarioDTO> getAllUsuarios() {
        // Obtiene todos y los mapea a DTO
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        // Utilizado principalmente por UserDetailsServiceImpl o validaciones
        return usuarioRepository.findByUsername(username);
    }
}