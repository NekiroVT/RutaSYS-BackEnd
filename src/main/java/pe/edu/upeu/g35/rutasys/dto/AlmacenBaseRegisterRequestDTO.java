package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO para la solicitud de registro de un nuevo Almacén Base (Input/Entrada).
 * DTO simple sin anotaciones de validación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenBaseRegisterRequestDTO {

    private String nombre;

    private String direccion;

    private Integer puertasDisponibles;

    private String estado;
}