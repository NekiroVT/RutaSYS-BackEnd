package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO utilizado para la presentación y actualización de los datos del Almacén Base.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenBaseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private Integer puertasDisponibles;
    private String estado;
}