package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ClienteDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ➡️ POST: /api/clientes (Creación)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Cliente>> createCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteService.save(cliente);

        ApiResponseDTO<Cliente> response = ApiResponseDTO.<Cliente>builder()
                .data(newCliente)
                .status(HttpStatus.CREATED.value())
                .message("Cliente registrado exitosamente.")
                .success(true)
                .build();

        // Uso de la sintaxis fluida para HttpStatus.CREATED (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➡️ PUT: /api/clientes/{id} (Actualización)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Cliente>> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        clienteDetails.setId(id);
        Cliente updatedCliente = clienteService.save(clienteDetails);

        ApiResponseDTO<Cliente> response = ApiResponseDTO.<Cliente>builder()
                .data(updatedCliente)
                .status(HttpStatus.OK.value())
                .message("Cliente actualizado exitosamente.")
                .success(true)
                .build();
        // Uso de la sintaxis fluida para HttpStatus.OK (200)
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/clientes (Listar todos)
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ClienteDTO>>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.getAllClientes();

        ApiResponseDTO<List<ClienteDTO>> response = ApiResponseDTO.<List<ClienteDTO>>builder()
                .data(clientes)
                .status(HttpStatus.OK.value())
                .message("Lista de clientes obtenida.")
                .success(true)
                .build();

        // Uso de la sintaxis fluida para HttpStatus.OK (200)
        return ResponseEntity.ok(response);
    }

    // ➡️ GET: /api/clientes/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.getClienteDTO(id)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + id + " no encontrado."));

        ApiResponseDTO<ClienteDTO> response = ApiResponseDTO.<ClienteDTO>builder()
                .data(cliente)
                .status(HttpStatus.OK.value())
                .message("Cliente obtenido.")
                .success(true)
                .build();
        // Uso de la sintaxis fluida para HttpStatus.OK (200)
        return ResponseEntity.ok(response);
    }

    // ➡️ DELETE: /api/clientes/{id} (Eliminación con cuerpo estructurado usando sintaxis fluida)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .data(null)
                .status(HttpStatus.OK.value()) // Se usa 200 OK para permitir el cuerpo JSON
                .message("Cliente eliminado exitosamente.")
                .success(true)
                .build();

        // Uso de ResponseEntity.ok() para devolver el JSON con código 200
        return ResponseEntity.ok(response);
    }
}