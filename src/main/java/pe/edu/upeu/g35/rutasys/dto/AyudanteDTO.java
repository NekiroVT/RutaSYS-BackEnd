package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO utilizado para la presentación de los datos del Ayudante (listados y detalles).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AyudanteDTO {

    private Long id;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;
    private String estado;

    // Información del Usuario asociado (para mapeo relacional)
    private Long usuarioId;
    private String username;
}