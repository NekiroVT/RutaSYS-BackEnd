package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.dto.ChoferDTO;
import pe.edu.upeu.g35.rutasys.service.service.ChoferService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/choferes")
public class ChoferController {

    private final ChoferService choferService;

    @Autowired
    public ChoferController(ChoferService choferService) {
        this.choferService = choferService;
    }

    // ➡️ POST: /api/choferes
    @PostMapping
    public ResponseEntity<ChoferDTO> createChofer(@RequestBody Chofer chofer) {
        // Usa registerChofer para la lógica de validación de usuario
        ChoferDTO newChofer = choferService.registerChofer(chofer);
        return new ResponseEntity<>(newChofer, HttpStatus.CREATED);
    }

    // ➡️ GET: /api/choferes
    @GetMapping
    public ResponseEntity<List<ChoferDTO>> getAllChoferes() {
        List<ChoferDTO> choferes = choferService.getAllChoferes();
        return ResponseEntity.ok(choferes);
    }

    // ➡️ GET: /api/choferes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ChoferDTO> getChoferById(@PathVariable Long id) {
        // Se usa getChofer(Long id) del servicio que lanza excepción si no existe.
        ChoferDTO chofer = choferService.getChofer(id);
        return ResponseEntity.ok(chofer);
    }

    // ➡️ DELETE: /api/choferes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChofer(@PathVariable Long id) {
        choferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}