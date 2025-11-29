package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.RegistroLlegadaChoferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/llegadas")
public class RegistroLlegadaChoferController {

    private final RegistroLlegadaChoferService registroService;

    public RegistroLlegadaChoferController(RegistroLlegadaChoferService registroService) {
        this.registroService = registroService;
    }

    // ➡️ 1. POST: /api/llegadas (Crear Registro de Llegada)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<RegistroLlegadaChoferDTO>> registrarLlegada(@RequestBody RegistroLlegadaChofer registro) {
        RegistroLlegadaChoferDTO newRegistro = registroService.registrarLlegada(registro);

        ApiResponseDTO<RegistroLlegadaChoferDTO> response = ApiResponseDTO.<RegistroLlegadaChoferDTO>builder()
                .data(newRegistro)
                .status(HttpStatus.CREATED.value())
                .message("Llegada registrada exitosamente.")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➡️ 2. PUT: /api/llegadas/{id} (Actualizar Registro) ⬅️ AÑADIDO
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RegistroLlegadaChofer>> updateRegistro(@PathVariable Long id, @RequestBody RegistroLlegadaChofer registroDetails) {

        // Aseguramos que el ID del path se use en la entidad
        registroDetails.setId(id);
        // Usamos el save genérico que maneja el update
        RegistroLlegadaChofer updatedRegistro = registroService.save(registroDetails);

        ApiResponseDTO<RegistroLlegadaChofer> response = ApiResponseDTO.<RegistroLlegadaChofer>builder()
                .data(updatedRegistro)
                .status(HttpStatus.OK.value())
                .message("Registro de llegada actualizado exitosamente.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ 3. GET: /api/llegadas (Listar todos) ⬅️ AÑADIDO
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RegistroLlegadaChofer>>> getAllRegistros() {
        // Devolvemos la entidad, ya que no definimos un DTO de lista específico
        List<RegistroLlegadaChofer> registros = registroService.findAll();

        ApiResponseDTO<List<RegistroLlegadaChofer>> response = ApiResponseDTO.<List<RegistroLlegadaChofer>>builder()
                .data(registros)
                .status(HttpStatus.OK.value())
                .message("Lista de registros de llegada obtenida.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ 4. GET: /api/llegadas/{id} (Buscar por ID) ⬅️ AÑADIDO
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RegistroLlegadaChoferDTO>> getRegistroById(@PathVariable Long id) {
        RegistroLlegadaChoferDTO registro = registroService.getRegistroDTO(id)
                .orElseThrow(() -> new RuntimeException("Registro con ID " + id + " no encontrado."));

        ApiResponseDTO<RegistroLlegadaChoferDTO> response = ApiResponseDTO.<RegistroLlegadaChoferDTO>builder()
                .data(registro)
                .status(HttpStatus.OK.value())
                .message("Registro de llegada obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // ➡️ 5. DELETE: /api/llegadas/{id} (Eliminar) ⬅️ AÑADIDO
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteRegistro(@PathVariable Long id) {
        registroService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Registro de llegada eliminado exitosamente.")
                .success(true)
                .build();

        // Uso de ResponseEntity.ok() para devolver el JSON con código 200
        return ResponseEntity.ok(response);
    }

    // ➡️ (El GET por manifiesto/mvId que ya tenías sigue aquí)
    @GetMapping("/manifiesto/{mvId}")
    public ResponseEntity<ApiResponseDTO<RegistroLlegadaChoferDTO>> getRegistroByManifiestoVehiculoId(@PathVariable Long mvId) {
        RegistroLlegadaChoferDTO registro = registroService.getRegistroByManifiestoVehiculoId(mvId)
                .orElseThrow(() -> new RuntimeException("Registro de llegada no encontrado para la asignación ID: " + mvId));

        ApiResponseDTO<RegistroLlegadaChoferDTO> response = ApiResponseDTO.<RegistroLlegadaChoferDTO>builder()
                .data(registro)
                .status(HttpStatus.OK.value())
                .message("Registro obtenido.")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}