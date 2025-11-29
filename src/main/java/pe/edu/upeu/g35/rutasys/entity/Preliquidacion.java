package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PRELIQUIDACION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preliquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRELIQUIDACION")
    @SequenceGenerator(name = "SEQ_PRELIQUIDACION", sequenceName = "SEQ_PRELIQUIDACION", allocationSize = 1)
    @Column(name = "ID_PRELIQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_HOJA_VIAJE", nullable = false)
    private HojaViaje hojaViaje;

    @Column(name = "TOTAL_GASTOS_PRELIQ", precision = 10, scale = 2)
    private BigDecimal totalGastosPreliq;

    @Column(name = "TOTAL_PAGAR_PRELIQ", precision = 10, scale = 2)
    private BigDecimal totalPagarPreliq;

    @Column(name = "ESTADO_PRELIQ", nullable = false, length = 20)
    private String estado;

    @Column(name = "FECHA_GENERACION_PRELIQ", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(name = "FECHA_PAGO_PRELIQ")
    private LocalDate fechaPago;
}