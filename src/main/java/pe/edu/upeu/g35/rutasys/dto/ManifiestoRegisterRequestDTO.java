package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para la solicitud de registro de un nuevo Manifiesto (Input/Entrada).
 * Solo incluye los datos que el cliente debe proporcionar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManifiestoRegisterRequestDTO {

    // Identificador del Cliente que realiza la solicitud (Foreign Key)
    private Long idCliente;

    private String codManifiesto;
    private String tipoCarga;
    private BigDecimal volumenTotalM3;
    private String almacenOrigen;
    private LocalDate fechaRecojo;
    private String horaRecojo;
    private String observaciones;

    // NOTA: fechaSolicitud y estado se establecerán automáticamente en el servicio.
}