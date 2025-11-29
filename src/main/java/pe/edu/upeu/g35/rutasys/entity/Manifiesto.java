package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "SOLICITUD_MANIFIESTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manifiesto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOL_MANIFIESTO")
    @SequenceGenerator(name = "SEQ_SOL_MANIFIESTO", sequenceName = "SEQ_SOL_MANIFIESTO", allocationSize = 1)
    @Column(name = "IDMANIFIESTO")
    private Long id;

    @Column(name = "COD_MANIFIESTO", nullable = false, length = 20)
    private String codManifiesto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDCLIENTE", nullable = false)
    private Cliente cliente;

    @Column(name = "FECHA_SOLICITUD", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "TIPO_CARGA", length = 100)
    private String tipoCarga;

    @Column(name = "VOLUMEN_TOTAL_M3", precision = 10, scale = 2)
    private BigDecimal volumenTotalM3;

    @Column(name = "ALMACEN_ORIGEN", length = 100)
    private String almacenOrigen;

    @Column(name = "FECHA_RECOJO")
    private LocalDate fechaRecojo;

    @Column(name = "HORA_RECOJO", length = 5)
    private String horaRecojo;

    @Column(name = "OBSERVACIONES", length = 300)
    private String observaciones;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}