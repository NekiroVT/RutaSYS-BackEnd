package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para la presentación de los datos del Manifiesto (Output/Salida).
 * Contiene la información de la solicitud más detalles del Cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManifiestoDTO {

    private Long id;
    private String codManifiesto;

    // --- Datos del Cliente (Aplanados desde la entidad Cliente) ---
    private Long clienteId;
    private String clienteRuc;
    private String clienteRazonSocial;

    // --- Datos del Manifiesto ---
    private LocalDate fechaSolicitud;
    private String tipoCarga;
    private BigDecimal volumenTotalM3;
    private String almacenOrigen;
    private LocalDate fechaRecojo;
    private String horaRecojo;
    private String observaciones;
    private String estado;
}