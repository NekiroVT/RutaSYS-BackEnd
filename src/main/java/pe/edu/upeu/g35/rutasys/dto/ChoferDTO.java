package pe.edu.upeu.g35.rutasys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoferDTO {

    private Long id;

    // --- Datos Personales del Chofer ---
    private String nombreCompleto;
    private String dni;
    private String licencia;
    private String telefono;
    private String correo;
    private String estado;

    // --- Datos de Referencia al Usuario (Security) ---
    private Long usuarioId;
    private String username;
}