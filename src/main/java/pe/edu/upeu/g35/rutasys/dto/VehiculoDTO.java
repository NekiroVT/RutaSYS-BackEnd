package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class VehiculoDTO {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private String tipo; // Ej: Camión, Furgoneta, Remolque
    private Integer capacidadVolumenM3;
    private Integer capacidadPesoKg;
    private String estado; // Ej: Operativo, En Mantenimiento, Fuera de Servicio
    private String observaciones;
}