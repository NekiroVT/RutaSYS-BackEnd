package pe.edu.upeu.g35.rutasys.controller.respuesta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {

    // Indica el estado de la operación (ej: 200, 201)
    private Integer status;

    // Mensaje para el cliente (ej: "Creación exitosa")
    private String message;

    // Los datos que se devuelven (ej: ChoferDTO)
    private T data;

    // (Opcional) Indica si la operación fue exitosa (true/false)
    private boolean success;
}