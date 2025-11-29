package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class AyudanteDTO {
    private Long id;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;
    private String estado;

    // Datos de la relación con Usuario
    private Long usuarioId;
    private String username;
}