package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProgPreliminarDetalleDTO {
    private Long id;
    private Long manifiestoVehiculoId;
    private Long tiendaId;
    private String nombreTienda;
    private String pex;
    private BigDecimal volumenM3;
    private String documentacion;
    private String estado; // Pendiente / En Camino / Entregado
}