package pe.edu.upeu.g35.rutasys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoferRegisterRequestDTO {

    // Datos de usuario
    private String username;
    private String password;

    // Datos del chofer
    private String nombreCompleto;
    private String dni;
    private String licencia;
    private String telefono;
    private String correo;
    private String estado;

    private String imagenUrl; // ✅ NUEVO
}
