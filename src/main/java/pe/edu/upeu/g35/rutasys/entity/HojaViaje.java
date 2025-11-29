package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "HOJA_VIAJE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HojaViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HOJA_VIAJE")
    @SequenceGenerator(name = "SEQ_HOJA_VIAJE", sequenceName = "SEQ_HOJA_VIAJE", allocationSize = 1)
    @Column(name = "ID_HOJA_VIAJE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_MANIFIESTO", nullable = false)
    private Manifiesto manifiesto;

    @Column(name = "FECHA_VIAJE", nullable = false)
    private LocalDate fechaViaje;

    @Column(name = "METRAJE_TOTAL_VIAJE", precision = 8, scale = 2)
    private BigDecimal metrajeTotalViaje;

    @Column(name = "TIPO_RUTA_VIAJE", nullable = false, length = 20)
    private String tipoRuta;

    @Column(name = "NIVEL_CARGA_VIAJE", nullable = false, length = 20)
    private String nivelCarga;

    @Column(name = "ESTADO_HOJA_VIAJE", nullable = false, length = 20)
    private String estado;
}