package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RegistroLlegadaChoferDTO {
    private Long id;
    private Long manifiestoVehiculoId;
    private Long choferId;
    private LocalDateTime fechaHoraLlegada;
    private String ubicacionTexto;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String fotoEvidencia;
    private String estadoLlegada;

    private String nombreChofer; // ⬅️ CAMPO AÑADIDO PARA LA VISTA DE CONFIRMACIÓN
}