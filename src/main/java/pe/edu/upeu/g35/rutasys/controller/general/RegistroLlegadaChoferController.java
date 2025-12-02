package pe.edu.upeu.g35.rutasys.controller;

import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.AsistenciaEstadoDTO; // ⬅️ DTO de Consulta de Estado
import pe.edu.upeu.g35.rutasys.service.service.RegistroLlegadaChoferService;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/llegadas-chofer")
public class RegistroLlegadaChoferController {

    private final RegistroLlegadaChoferService registroService;

    public RegistroLlegadaChoferController(RegistroLlegadaChoferService registroService) {
        this.registroService = registroService;
    }

    // -------------------------------------------------------------------------
    // 1. CREATE: Registra la llegada (Disparador de Asistencia Masiva)
    // -------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponseDTO<RegistroLlegadaChoferDTO>> registerLlegada(
            @RequestBody RegistroLlegadaChoferRegisterRequestDTO request) {

        try {
            // Este método busca registros ACTIVO y los cambia a PRESENTE masivamente.
            RegistroLlegadaChoferDTO nuevoRegistro = registroService.register(request);

            ApiResponseDTO<RegistroLlegadaChoferDTO> response = ApiResponseDTO.<RegistroLlegadaChoferDTO>builder()
                    .data(nuevoRegistro)
                    .status(HttpStatus.CREATED.value())
                    .message("Registro de llegada completado con éxito. Todas las tareas asociadas fueron marcadas como PRESENTE.")
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Manejo de errores de negocio (Ej: No tiene estado ACTIVO)
            ApiResponseDTO<RegistroLlegadaChoferDTO> errorResponse = ApiResponseDTO.<RegistroLlegadaChoferDTO>builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error al registrar llegada: " + e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // -------------------------------------------------------------------------
    // 2. READ: Consulta el Estado de Asistencia Dominante ⬅️ NUEVO ENDPOINT
    // -------------------------------------------------------------------------

    /**
     * Endpoint utilizado por el frontend para saber el estado actual del chofer (ACTIVO, PRESENTE, NO_INICIADO).
     * URL: /api/llegadas-chofer/estado/{idChofer}
     */
    @GetMapping("/estado/{idChofer}")
    public ResponseEntity<ApiResponseDTO<AsistenciaEstadoDTO>> getEstadoAsistencia(@PathVariable Long idChofer) {

        // Llama al nuevo método inteligente que creamos en el servicio
        Optional<AsistenciaEstadoDTO> estadoOpt = registroService.getEstadoAsistenciaDominante(idChofer);

        if (estadoOpt.isPresent()) {
            ApiResponseDTO<AsistenciaEstadoDTO> response = ApiResponseDTO.<AsistenciaEstadoDTO>builder()
                    .data(estadoOpt.get())
                    .status(HttpStatus.OK.value())
                    .message("Estado de asistencia obtenido con éxito.")
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            // Esto ocurre si el Chofer no tiene ninguna asignación RLC (ni siquiera NO_INICIADO)
            ApiResponseDTO<AsistenciaEstadoDTO> response = ApiResponseDTO.<AsistenciaEstadoDTO>builder()
                    .data(AsistenciaEstadoDTO.builder().estadoDominante("NO_ASIGNADO").build())
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("El chofer no tiene asignaciones pendientes en el sistema.")
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // -------------------------------------------------------------------------
    // 3. OPERACIÓN ADMINISTRATIVA: Habilitar Asistencia ⬅️ NUEVO ENDPOINT
    // -------------------------------------------------------------------------

    /**
     * Endpoint administrativo para habilitar la asistencia (cambia NO_INICIADO a ACTIVO).
     * URL: /api/llegadas-chofer/habilitar/{idManifiestoVehiculo}
     */
    @PostMapping("/habilitar/{idManifiestoVehiculo}")
    public ResponseEntity<ApiResponseDTO<Void>> habilitarAsistencia(@PathVariable Long idManifiestoVehiculo) {
        try {
            // Llama al método que cambia el estado de todos los RLCs del vehículo a ACTIVO
            registroService.habilitarAsistencia(idManifiestoVehiculo);
            ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.OK.value())
                    .message("Asistencias asociadas al vehículo habilitadas (ACTIVO).")
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // -------------------------------------------------------------------------
    // 4. READ ALL: Obtiene todos los registros de llegada.
    // -------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RegistroLlegadaChoferDTO>>> getAllRegistros() {

        List<RegistroLlegadaChoferDTO> registros = registroService.getAllRegistros();

        ApiResponseDTO<List<RegistroLlegadaChoferDTO>> response = ApiResponseDTO.<List<RegistroLlegadaChoferDTO>>builder()
                .data(registros)
                .status(HttpStatus.OK.value())
                .message("Registros de llegada obtenidos con éxito.")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // 5. DELETE: Elimina un registro por su ID.
    // -------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteRegistro(@PathVariable Long id) {

        try {
            registroService.delete(id);
            ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("Registro de llegada con ID " + id + " eliminado con éxito.")
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