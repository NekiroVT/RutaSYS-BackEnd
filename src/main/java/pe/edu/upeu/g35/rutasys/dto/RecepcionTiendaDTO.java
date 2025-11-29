package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecepcionTiendaDTO {
    private Long id;
    private Long progPreliminarDetalleId;
    private String estadoEntrega; // Debe ser 'ENTREGADO' o 'INCIDENCIA'
    private LocalDateTime fechaHoraRecepcion;
    private String observaciones;
    private String facturaPath;
    private String guiaRemisionPath;
    private String pickingPath;
}