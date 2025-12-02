package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// DTO para la solicitud de registro de un nuevo Ayudante, incluyendo credenciales de Usuario.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AyudanteRegisterRequestDTO {

    // --- Campos de Usuario para AuthService ---
    private String username;
    private String password;

    // --- Campos de Ayudante ---
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;

    // Campo 'estado' de la entidad
    private String estado;
}