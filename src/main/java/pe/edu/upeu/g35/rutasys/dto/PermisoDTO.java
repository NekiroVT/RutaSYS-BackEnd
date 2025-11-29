package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class PermisoDTO {
    private Long id;
    private String nombre; // Ej: "CREATE_CHOFER", "VIEW_ALMACENES"
    private String descripcion;
}