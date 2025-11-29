package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "VEHICULO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VEHICULO")
    @SequenceGenerator(name = "SEQ_VEHICULO", sequenceName = "SEQ_VEHICULO", allocationSize = 1)
    @Column(name = "IDVEHICULO")
    private Long id;

    @Column(name = "PLACA", nullable = false, length = 10)
    private String placa;

    @Column(name = "MARCA", length = 50)
    private String marca;

    @Column(name = "MODELO", length = 50)
    private String modelo;

    @Column(name = "CAPACIDAD_M3", precision = 10, scale = 2)
    private BigDecimal capacidadM3;

    @Column(name = "FECHA_ULT_MANT_VEHICULO")
    private LocalDate fechaUltMantenimiento;

    @Column(name = "ESTADO_VEHICULO", nullable = false, length = 30)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_ALMACEN_BASE", nullable = false)
    private Almacen almacenBase;
}