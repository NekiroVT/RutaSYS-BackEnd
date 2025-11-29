package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "MANTENIMIENTO_VEHICULAR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MantenimientoVehicular {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MANT_VEHICULAR")
    @SequenceGenerator(name = "SEQ_MANT_VEHICULAR", sequenceName = "SEQ_MANT_VEHICULAR", allocationSize = 1)
    @Column(name = "ID_MANTENIMIENTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "ID_PROVEEDOR", nullable = false)
    private ProveedorMantenimiento proveedor;

    @Column(name = "TIPO_MANTENIMIENTO", nullable = false, length = 20)
    private String tipoMantenimiento;

    @Column(name = "FECHA_PROGRAMADA_MANT", nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = "FECHA_REALIZADA_MANT")
    private LocalDate fechaRealizada;

    @Column(name = "KILOMETRAJE_MANT", nullable = false)
    private Integer kilometraje;

    @Column(name = "COSTO_MANT", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(name = "REPORTE_URL_MANT", length = 255)
    private String reporteUrl;

    @Column(name = "ESTADO_MANTENIMIENTO", nullable = false, length = 20)
    private String estado;
}