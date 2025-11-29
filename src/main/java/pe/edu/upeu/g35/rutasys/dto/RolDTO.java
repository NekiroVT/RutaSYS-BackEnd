package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class RolDTO {
    private Long id;
    private String nombre;
    // Podrías añadir List<PermisoDTO> si vas a mostrar los permisos asignados
}