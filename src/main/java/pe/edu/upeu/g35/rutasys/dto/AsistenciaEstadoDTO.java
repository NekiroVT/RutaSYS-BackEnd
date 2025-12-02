package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO para informar al frontend sobre el estado de la asistencia del Chofer.
 * Contiene el estado dominante y los IDs necesarios para disparar la acción de registro.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaEstadoDTO {

    // El estado actual que domina (PRESENTE, ACTIVO, NO_INICIADO, FALTO, NO_ASIGNADO)
    private String estadoDominante;

    // El ID del ManifiestoVehiculo que debe usarse para el POST de registro.
    // Es el DISPARADOR. Null si no hay ACTIVO.
    private Long idManifiestoVehiculoDisparador;

    // Información de Trazabilidad y UX
    private Long idChofer; // ⬅️ Añadido para trazar el Chofer
    private String choferNombreCompleto; // ⬅️ Añadido para la UX del frontend
    private Long idVehiculoAsignado;
}