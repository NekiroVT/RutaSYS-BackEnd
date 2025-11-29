package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class TiendaDTO {
    private Long id;
    private String nombreTienda;
    private String distrito;
    private String direccion;
    private String canal;
    private String destinatario;
    private String vhInicio;
    private String vhFin;
    private String numeroPrincipal;
    private String numeroOpcional;
    private String emailTienda;
    private String estado;
}