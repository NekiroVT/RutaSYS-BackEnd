package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO utilizado para la presentación del registro de llegada del Chofer (Output/Salida).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroLlegadaChoferDTO {

    private Long id;
    private LocalDateTime fechaHoraLlegada;
    private String ubicacionTexto;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String fotoEvidencia;
    private String estadoLlegada;

    // --- Datos de ManifiestoVehiculo (Asignación) ---
    private Long manifiestoVehiculoId;
    private String manifiestoVehiculoCod; // Asumo que podemos obtener el codManifiesto de M-V

    // --- Datos de Chofer ---
    private Long choferId;
    private String choferNombreCompleto;
}