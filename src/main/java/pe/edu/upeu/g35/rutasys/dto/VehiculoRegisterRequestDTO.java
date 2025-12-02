package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para la solicitud de registro de un nuevo Vehículo (Input/Entrada).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoRegisterRequestDTO {

    private String placa;
    private String marca;
    private String modelo;
    private BigDecimal capacidadM3;

    // Campo opcional, puede ser nulo o establecido en el servicio
    private LocalDate fechaUltMantenimiento;

    // El estado inicial del vehículo se fija en el servicio ('OPERATIVO')
    private String estado;

    // Identificador del Almacén Base (Foreign Key)
    private Long idAlmacenBase;
}