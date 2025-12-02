package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO para la solicitud de asignación de recursos a un Manifiesto (Input/Entrada).
 * Solo incluye los IDs de las entidades relacionadas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManifiestoVehiculoRegisterRequestDTO {

    // IDs de las Entidades relacionadas (FKs)
    private Long idManifiesto;
    private Long idVehiculo;
    private Long idChofer; // Opcional en la entidad original
    private Long idAyudante; // Opcional en la entidad original

    // El estado inicial (Ej: "ASIGNADO")
    private String estado;
}