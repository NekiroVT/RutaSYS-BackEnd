package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// Se eliminan las anotaciones de validación para ser consistente con el patrón simple de registro.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministradorRegisterRequestDTO {

    // --- Campos de Usuario ---
    private String username;
    private String password;

    // --- Campos de Administrador ---
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String cargo; // Ej: Jefe de Operaciones

    // Se añade el estado para ser consistente con el ChoferDTO, aunque el servicio lo fija en "ACTIVO".
    private String estado;
}