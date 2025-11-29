package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InfoStopViewDTO { // ⬅️ NOMBRE CAMBIADO
    // Info del Vehículo (Placa)
    private String placa;

    // Info de la Tienda
    private String nombreTienda;
    private String distritoTienda;
    private String direccionTienda;
    private String canal;
    private String destinatario;
    private String vhInicio;
    private String vhFin;
    private String numeroPrincipal;
    private String numeroOpcional;
    private String correoTienda;

    // Info del Pedido (Detalle)
    private String documentacionRequerida; // Mapeado de ProgPreliminarDetalle.documentacion
    private String pex;
    private BigDecimal volumenM3;
    private String estado;

    // Asumo que tu consulta JPQL usará un constructor para llenarlo.
}