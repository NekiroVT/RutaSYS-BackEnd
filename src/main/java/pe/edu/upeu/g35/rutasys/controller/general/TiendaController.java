package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Tienda;
import pe.edu.upeu.g35.rutasys.dto.TiendaDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.TiendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    // ➡️ POST: /api/tiendas (Creación)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Tienda>> createTienda(@RequestBody Tienda tienda) {
        Tienda newTienda = tiendaService.save(tienda);

        ApiResponseDTO<Tienda> response = ApiResponseDTO.<Tienda>builder()
                .data(newTienda)
                .status(HttpStatus.CREATED.value())
                .message("Tienda registrada exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➡️ PUT: /api/tiendas/{id} (Actualización)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Tienda>> updateTienda(@PathVariable Long id, @RequestBody Tienda tiendaDetails) {
        tiendaDetails.setId(id);
        Tienda updatedTienda = tiendaService.save(tiendaDetails);

        ApiResponseDTO<Tienda> response = ApiResponseDTO.<Tienda>builder()
                .data(updatedTienda)
                .status(HttpStatus.OK.value())
                .message("Tienda actualizada exitosamente.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/tiendas (Listar todos)
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TiendaDTO>>> getAllTiendas() {
        List<TiendaDTO> tiendas = tiendaService.getAllTiendas();

        ApiResponseDTO<List<TiendaDTO>> response = ApiResponseDTO.<List<TiendaDTO>>builder()
                .data(tiendas)
                .status(HttpStatus.OK.value())
                .message("Lista de tiendas obtenida.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/tiendas/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TiendaDTO>> getTiendaById(@PathVariable Long id) {
        TiendaDTO tienda = tiendaService.getTiendaDTO(id)
                .orElseThrow(() -> new RuntimeException("Tienda con ID " + id + " no encontrada."));

        ApiResponseDTO<TiendaDTO> response = ApiResponseDTO.<TiendaDTO>builder()
                .data(tienda)
                .status(HttpStatus.OK.value())
                .message("Tienda obtenida.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/tiendas/{id} (Eliminación con cuerpo estructurado usando sintaxis fluida)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTienda(@PathVariable Long id) {
        tiendaService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Tienda eliminada exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}