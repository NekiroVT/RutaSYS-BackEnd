package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.AyudanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ayudantes")
public class AyudanteController {

    private final AyudanteService ayudanteService;

    public AyudanteController(AyudanteService ayudanteService) {
        this.ayudanteService = ayudanteService;
    }

    // ➡️ POST: /api/ayudantes (Creación) - Corregido a sintaxis fluida
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AyudanteDTO>> createAyudante(@RequestBody Ayudante ayudante) {
        AyudanteDTO newAyudante = ayudanteService.saveAyudante(ayudante);

        ApiResponseDTO<AyudanteDTO> response = ApiResponseDTO.<AyudanteDTO>builder()
                .data(newAyudante)
                .status(HttpStatus.CREATED.value())
                .message("Ayudante registrado exitosamente.")
                .success(true)
                .build();

        // ✅ Uso de sintaxis fluida: .status(201).body()
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➡️ PUT: /api/ayudantes/{id} (Actualización) - Usa sintaxis fluida
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AyudanteDTO>> updateAyudante(@PathVariable Long id, @RequestBody Ayudante ayudanteDetails) {

        ayudanteDetails.setId(id);
        AyudanteDTO updatedAyudante = ayudanteService.saveAyudante(ayudanteDetails);

        ApiResponseDTO<AyudanteDTO> response = ApiResponseDTO.<AyudanteDTO>builder()
                .data(updatedAyudante)
                .status(HttpStatus.OK.value())
                .message("Ayudante actualizado exitosamente.")
                .success(true)
                .build();
        // ✅ Uso de sintaxis fluida: .ok()
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/ayudantes (Listar todos)
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AyudanteDTO>>> getAllAyudantes() {
        List<AyudanteDTO> ayudantes = ayudanteService.getAllAyudantes();

        ApiResponseDTO<List<AyudanteDTO>> response = ApiResponseDTO.<List<AyudanteDTO>>builder()
                .data(ayudantes)
                .status(HttpStatus.OK.value())
                .message("Lista de ayudantes obtenida.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/ayudantes/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AyudanteDTO>> getAyudanteById(@PathVariable Long id) {
        AyudanteDTO ayudante = ayudanteService.getAyudanteDTO(id)
                .orElseThrow(() -> new RuntimeException("Ayudante con ID " + id + " no encontrado."));

        ApiResponseDTO<AyudanteDTO> response = ApiResponseDTO.<AyudanteDTO>builder()
                .data(ayudante)
                .status(HttpStatus.OK.value())
                .message("Ayudante obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/ayudantes/{id} (CORREGIDO: Ahora retorna el cuerpo estructurado)
    @DeleteMapping("/{id}")
    // ⬅️ La firma debe coincidir con el cuerpo que estás devolviendo
    public ResponseEntity<ApiResponseDTO<Void>> deleteAyudante(@PathVariable Long id) {
        ayudanteService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value()) // ✅ Usamos 200 OK para devolver el JSON
                .message("Ayudante eliminado exitosamente.")
                .success(true)
                .build();

        // ✅ Usamos ResponseEntity.ok() (equivalente a .status(200).body())
        return ResponseEntity.ok(response);
    }
}