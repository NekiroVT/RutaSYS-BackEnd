package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class RolUsuarioDTO {
    private Long id;
    private Long rolId;
    private Long usuarioId;
    // Opcional: private String rolNombre;
}