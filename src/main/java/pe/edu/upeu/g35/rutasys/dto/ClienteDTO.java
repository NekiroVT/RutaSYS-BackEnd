package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String contacto;
    private String telefono;
    private String correo;
    private String estado;

    private Long usuarioId;
    private String username;
}