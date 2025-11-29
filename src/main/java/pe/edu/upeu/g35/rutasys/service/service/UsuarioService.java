package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.dto.UsuarioDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface UsuarioService extends GenericService<Usuario, Long> {

    // Métodos para el Controller
    Optional<UsuarioDTO> getUsuarioDTO(Long id);

    List<UsuarioDTO> getAllUsuarios();

    // Método de búsqueda especializado (usado en UserDetailsServiceImpl)
    Optional<Usuario> findByUsername(String username);
}