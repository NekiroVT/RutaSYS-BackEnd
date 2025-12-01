package pe.edu.upeu.g35.rutasys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorDTO {

    private Long id;

    // --- Datos Personales del Administrador ---
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String cargo;
    private String estado;
    private String imagenUrl; // ✅ AÑADIDO para coincidir con la Entidad

    // --- Datos de Referencia al Usuario (Security) ---
    private Long usuarioId;
    private String username;
}