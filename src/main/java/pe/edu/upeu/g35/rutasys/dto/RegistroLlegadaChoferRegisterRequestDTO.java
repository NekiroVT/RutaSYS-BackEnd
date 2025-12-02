package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * DTO para la solicitud de registro de la llegada de un Chofer (Input/Entrada).
 * Esta solicitud actúa como disparador de la asistencia masiva para el VEHÍCULO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroLlegadaChoferRegisterRequestDTO {

    // IDs de las Entidades obligatorias (FKs)
    private Long idManifiestoVehiculo; // ⬅️ Asignación que dispara la acción
    private Long idChofer; // ⬅️ Chofer que registra (para trazabilidad)

    // Datos de Geolocalización
    private String ubicacionTexto;
    private BigDecimal latitud;
    private BigDecimal longitud;

    // Evidencia
    private String fotoEvidencia;

    // Estado (se ignora, ya que el servicio lo fija a "PRESENTE")
    private String estadoLlegada;
}