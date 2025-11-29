package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    // La contraseña nunca se expone en el DTO de lectura

    // Información del Rol para gestión
    private Long rolId;
    private String rolNombre;
}