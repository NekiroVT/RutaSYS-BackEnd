package pe.edu.upeu.g35.rutasys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleUserDTO {
    private Long usuarioId;
    private Long choferId;       // null si no es chofer
    private Long administradorId;
    private String nombre;
    private String rol;
    private String route;
    private String imagenUrl;    // ✅ imagen del chofer si aplica
}
