package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO utilizado para la presentación de la asignación de Manifiesto-Vehículo (Output/Salida).
 * Contiene información aplanada de Manifiesto, Vehículo, Chofer y Ayudante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManifiestoVehiculoDTO {

    private Long id;
    private String estado;

    // --- Datos de Manifiesto ---
    private Long manifiestoId;
    private String manifiestoCod; // Código del manifiesto

    // --- Datos de Vehículo ---
    private Long vehiculoId;
    private String vehiculoPlaca;

    // --- Datos de Chofer ---
    private Long choferId;
    private String choferNombreCompleto;

    // --- Datos de Ayudante ---
    private Long ayudanteId;
    private String ayudanteNombreCompleto;
}