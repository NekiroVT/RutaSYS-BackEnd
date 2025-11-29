package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentationStopViewDTO {

    // --- Campos de la Vista ---
    private Long manifiestoId;
    private String codManifiesto;
    private String placa;
    private String nombreTienda;
    private Long detalleId;
    private String estadoCarga;
    private LocalDateTime fechaHoraRecepcion;
    private String documentacionStatus;

    // Constructor vacío (necesario para Lombok/mapeo estándar)
    public DocumentationStopViewDTO() {
    }

    // 🛑 CONSTRUCTOR REQUERIDO POR LA CONSULTA JPQL (@Query) 🛑
    // Los nombres de los parámetros no importan, pero el ORDEN y el TIPO deben ser EXACTOS.
    public DocumentationStopViewDTO(
            String codManifiesto,
            String placa,
            String nombreTienda,
            String estadoCarga, // Mapea a d.estado
            LocalDateTime fechaHoraRecepcion) // Mapea a r.fechaHoraRecepcion
    {
        // Asignación de los valores del constructor a los campos del DTO
        this.codManifiesto = codManifiesto;
        this.placa = placa;
        this.nombreTienda = nombreTienda;
        this.estadoCarga = estadoCarga;
        this.fechaHoraRecepcion = fechaHoraRecepcion;

        // Lógica para establecer el estado del botón (documentacionStatus)
        this.documentacionStatus = "RECIBIDO".equals(estadoCarga) ? "Ver" : "Ingresar";
    }
}