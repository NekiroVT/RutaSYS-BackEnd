package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class ManifiestoVehiculoDTO {
    private Long id;
    private Long manifiestoId;
    private Long vehiculoId;
    private Long choferId;
    private Long ayudanteId;
    private String estado;
}