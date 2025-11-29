package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ManifiestoDTO {
    private Long id;
    private String codManifiesto;
    private Long clienteId;
    private String razonSocialCliente;
    private LocalDate fechaSolicitud;
    private String tipoCarga;
    private BigDecimal volumenTotalM3;
    private String almacenOrigen;
    private LocalDate fechaRecojo;
    private String horaRecojo;
    private String observaciones;
    private String estado;
}