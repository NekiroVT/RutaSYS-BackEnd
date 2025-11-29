package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRACKING_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRACKING_EVENT")
    @SequenceGenerator(name = "SEQ_TRACKING_EVENT", sequenceName = "SEQ_TRACKING_EVENT", allocationSize = 1)
    @Column(name = "IDEVENT")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDMANIF_VEH", nullable = false)
    private ManifiestoVehiculo manifiestoVehiculo;

    @ManyToOne
    @JoinColumn(name = "IDPROG_DET")
    private ProgPreliminarDetalle progPreliminarDetalle;

    @Column(name = "TIPO_EVENTO", nullable = false, length = 40)
    private String tipoEvento;

    @Column(name = "DESCRIPCION", length = 400)
    private String descripcion;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "FOTO_PATH", length = 300)
    private String fotoPath;

    @Column(name = "LATITUD", precision = 10, scale = 6)
    private BigDecimal latitud;

    @Column(name = "LONGITUD", precision = 10, scale = 6)
    private BigDecimal longitud;
}