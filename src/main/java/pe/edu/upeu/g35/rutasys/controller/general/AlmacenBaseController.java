package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseDTO;
import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.service.AlmacenBaseService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/almacenes-base")
public class AlmacenBaseController {

    private final AlmacenBaseService almacenBaseService;

    public AlmacenBaseController(AlmacenBaseService almacenBaseService) {
        this.almacenBaseService = almacenBaseService;
    }

    /**
     * 1. CREATE: Registra un nuevo Almacén Base.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AlmacenBaseDTO>> registerAlmacenBase(
            @RequestBody AlmacenBaseRegisterRequestDTO request) {

        try {
            AlmacenBaseDTO nuevoAlmacen = almacenBaseService.register(request);

            ApiResponseDTO<AlmacenBaseDTO> response = ApiResponseDTO.<AlmacenBaseDTO>builder()
                    .data(nuevoAlmacen)
                    .status(HttpStatus.CREATED.value())
                    .message("Almacén Base registrado con éxito.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            ApiResponseDTO<AlmacenBaseDTO> errorResponse = ApiResponseDTO.<AlmacenBaseDTO>builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error al registrar almacén: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 2. READ ALL: Obtiene todos los almacenes registrados.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AlmacenBaseDTO>>> getAllAlmacenesBase() {

        List<AlmacenBaseDTO> almacenes = almacenBaseService.getAllAlmacenesBase();

        ApiResponseDTO<List<AlmacenBaseDTO>> response = ApiResponseDTO.<List<AlmacenBaseDTO>>builder()
                .data(almacenes)
                .status(HttpStatus.OK.value())
                .message("Almacenes Base obtenidos con éxito.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 2. READ BY ID: Obtiene un almacén por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AlmacenBaseDTO>> getAlmacenBaseById(@PathVariable Long id) {

        Optional<AlmacenBaseDTO> almacenOpt = almacenBaseService.getAlmacenBaseDTO(id);

        if (almacenOpt.isPresent()) {
            ApiResponseDTO<AlmacenBaseDTO> response = ApiResponseDTO.<AlmacenBaseDTO>builder()
                    .data(almacenOpt.get())
                    .status(HttpStatus.OK.value())
                    .message("Almacén Base encontrado.")
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponseDTO<AlmacenBaseDTO> response = ApiResponseDTO.<AlmacenBaseDTO>builder()
                    .data(null)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Almacén Base con ID " + id + " no encontrado.")
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 3. UPDATE: Actualiza un Almacén Base existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AlmacenBaseDTO>> updateAlmacenBase(
            @PathVariable Long id,
            @RequestBody AlmacenBaseDTO updateDTO) {

        try {
            AlmacenBaseDTO updatedAlmacen = almacenBaseService.update(id, updateDTO);

            ApiResponseDTO<AlmacenBaseDTO> response = ApiResponseDTO.<AlmacenBaseDTO>builder()
                    .data(updatedAlmacen)
                    .status(HttpStatus.OK.value())
                    .message("Almacén Base actualizado con éxito.")
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            HttpStatus status = e.getMessage().contains("no encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            ApiResponseDTO<AlmacenBaseDTO> errorResponse = ApiResponseDTO.<AlmacenBaseDTO>builder()
                    .data(null)
                    .status(status.value())
                    .message("Error al actualizar almacén: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, status);
        }
    }

    /**
     * 4. DELETE: Elimina un almacén por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAlmacenBase(@PathVariable Long id) {

        try {
            almacenBaseService.delete(id);
            ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("Almacén Base con ID " + id + " eliminado con éxito.")
                    .success(true)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}