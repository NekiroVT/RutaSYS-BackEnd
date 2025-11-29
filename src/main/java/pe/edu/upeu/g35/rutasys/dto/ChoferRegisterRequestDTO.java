package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

// Clase para recibir los datos del JSON de registro
@Data
public class ChoferRegisterRequestDTO {
    // Campos necesarios para crear el Usuario
    private String username;
    private String password;
    // private Long rolId;         // ⬅️ ¡¡ELIMINADO!!

    // Campos necesarios para crear la entidad Chofer
    private String nombreCompleto;
    private String dni;
    private String licencia;
    private String telefono;
    private String correo;
}