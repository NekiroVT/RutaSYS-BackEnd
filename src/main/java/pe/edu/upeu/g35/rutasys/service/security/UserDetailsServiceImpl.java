package pe.edu.upeu.g35.rutasys.service.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.g35.rutasys.entity.Usuario; // ⬅️ Paquete de Entidad
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository; // ⬅️ Paquete de Repository

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // ⬅️ Buscar por username
        // Buscar al usuario por username en el repositorio
        Usuario usuario = usuarioRepository.findByUsername(username) // ⬅️ Método de búsqueda corregido
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Asumimos que el usuario siempre está activo si existe
        boolean enabled = true;

        // 🔥 Spring Security necesita esta información internamente
        return User.withUsername(usuario.getUsername()) // ⬅️ Usamos getUsername()
                .password(usuario.getPassword())
                .authorities(Collections.emptyList()) // Sin roles por ahora
                .disabled(!enabled)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }
}