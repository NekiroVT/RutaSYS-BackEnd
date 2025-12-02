package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PROG_PRELIMINAR_DETALLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgPreliminarDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROG_PRELIM_DET")
    @SequenceGenerator(name = "SEQ_PROG_PRELIM_DET", sequenceName = "SEQ_PROG_PRELIM_DET", allocationSize = 1)
    @Column(name = "IDPROG_DET")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDMANIF_VEH", nullable = false)
    private ManifiestoVehiculo manifiestoVehiculo; //CAMBIAR POR MANIFIESTO

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDTIENDA", nullable = false)
    private Tienda tienda;

    @Column(name = "PEX", length = 30)
    private String pex;

    @Column(name = "VOLUMEN_M3", precision = 10, scale = 2)
    private BigDecimal volumenM3;

    @Column(name = "DOCUMENTACION", length = 400)
    private String documentacion;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}