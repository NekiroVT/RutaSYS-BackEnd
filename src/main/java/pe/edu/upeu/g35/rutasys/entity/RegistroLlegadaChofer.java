package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRO_LLEGADA_CHOFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroLlegadaChofer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LLEGADA_CHOFER")
    @SequenceGenerator(name = "SEQ_LLEGADA_CHOFER", sequenceName = "SEQ_LLEGADA_CHOFER", allocationSize = 1)
    @Column(name = "IDLLEGADA")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDMANIF_VEH", nullable = false)
    private ManifiestoVehiculo manifiestoVehiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDCHOFER", nullable = false)
    private Chofer chofer;

    @Column(name = "FECHA_HORA_LLEG", nullable = false)
    private LocalDateTime fechaHoraLlegada;

    @Column(name = "UBICACION_TEXTO", length = 200)
    private String ubicacionTexto;

    @Column(name = "LATITUD", precision = 10, scale = 6)
    private BigDecimal latitud;

    @Column(name = "LONGITUD", precision = 10, scale = 6)
    private BigDecimal longitud;

    @Column(name = "FOTO_EVIDENCIA", length = 300)
    private String fotoEvidencia;

    @Column(name = "ESTADO_LLEGADA", nullable = false, length = 20)
    private String estadoLlegada;
}