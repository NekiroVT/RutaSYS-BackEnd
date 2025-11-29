package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MANIFIESTO_VEHICULO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManifiestoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MANIF_VEH")
    @SequenceGenerator(name = "SEQ_MANIF_VEH", sequenceName = "SEQ_MANIF_VEH", allocationSize = 1)
    @Column(name = "IDMANIF_VEH")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDMANIFIESTO", nullable = false)
    private Manifiesto manifiesto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDVEHICULO", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "IDCHOFER")
    private Chofer chofer;

    @ManyToOne
    @JoinColumn(name = "IDAYUDANTE")
    private Ayudante ayudante;

    @Column(name = "ESTADO", nullable = false, length = 30)
    private String estado;
}