package pe.edu.upeu.g35.rutasys.controller.general;
// O puedes crear un paquete 'pe.edu.upeu.g35.rutasys.controller.administracion'

import pe.edu.upeu.g35.rutasys.entity.Rol;
import pe.edu.upeu.g35.rutasys.dto.RolDTO;
import pe.edu.upeu.g35.rutasys.service.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles") // Endpoint REST para gestionar roles
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // POST /api/roles: Crea un nuevo rol
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        // Usa el método save() heredado del servicio genérico
        Rol newRol = rolService.save(rol);
        return new ResponseEntity<>(newRol, HttpStatus.CREATED);
    }

    // GET /api/roles: Lista todos los roles
    @GetMapping
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    // DELETE /api/roles/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}