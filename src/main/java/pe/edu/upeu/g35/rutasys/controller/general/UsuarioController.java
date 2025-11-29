package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.dto.UsuarioDTO;
import pe.edu.upeu.g35.rutasys.service.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // Endpoint REST para la gestión de usuarios
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ➡️ GET: /api/usuarios (Listar todos los usuarios como DTO)
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        // Usa el método DTO del servicio
        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // ➡️ GET: /api/usuarios/{id} (Buscar por ID y retornar DTO)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.getUsuarioDTO(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado."));
        return ResponseEntity.ok(usuario);
    }

    // ➡️ PUT: /api/usuarios/{id} (Actualizar la información del usuario)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        // Aquí deberías tener una lógica para verificar que el ID de la ruta coincida con el ID del cuerpo,
        // o usar un DTO de actualización.
        if (!usuarioDetails.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // NOTA: Para una actualización segura, el servicio debe manejar la encriptación
        // de la contraseña si se cambia, y la asignación del rol.
        Usuario updatedUsuario = usuarioService.save(usuarioDetails);

        // Retornamos el DTO del usuario actualizado
        return usuarioService.getUsuarioDTO(updatedUsuario.getId())
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ➡️ DELETE: /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}