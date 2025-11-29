package pe.edu.upeu.g35.rutasys.controller.general;

import pe.edu.upeu.g35.rutasys.entity.ProgPreliminarDetalle;
import pe.edu.upeu.g35.rutasys.dto.ProgPreliminarDetalleDTO;
import pe.edu.upeu.g35.rutasys.controller.respuesta.ApiResponseDTO;
import pe.edu.upeu.g35.rutasys.service.service.ProgPreliminarDetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/programacion-detalles")
public class ProgPreliminarDetalleController {

    private final ProgPreliminarDetalleService detalleService;

    public ProgPreliminarDetalleController(ProgPreliminarDetalleService detalleService) {
        this.detalleService = detalleService;
    }

    // ➡️ POST: /api/programacion-detalles (Crear o Actualizar una parada/detalle)
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ProgPreliminarDetalleDTO>> createDetalle(@RequestBody ProgPreliminarDetalle detalle) {
        ProgPreliminarDetalleDTO newDetalle = detalleService.saveDetalle(detalle);

        ApiResponseDTO<ProgPreliminarDetalleDTO> response = ApiResponseDTO.<ProgPreliminarDetalleDTO>builder()
                .data(newDetalle)
                .status(HttpStatus.CREATED.value())
                .message("Detalle de programación registrado.")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // [Se añadirían los endpoints GET por ID, GET por ManifiestoVehiculoId, PUT, DELETE]
}