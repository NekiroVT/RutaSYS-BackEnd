package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RECEPCION_TIENDA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEPCION_TIENDA")
    @SequenceGenerator(name = "SEQ_RECEPCION_TIENDA", sequenceName = "SEQ_RECEPCION_TIENDA", allocationSize = 1)
    @Column(name = "IDRECEPCION")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "IDPROG_DET", nullable = false, unique = true)
    private ProgPreliminarDetalle progPreliminarDetalle;


    @Column(name = "ESTADO_ENTREGA", nullable = false, length = 20)
    private String estadoEntrega;

    @Column(name = "FECHA_HORA_REC")
    private LocalDateTime fechaHoraRecepcion;

    @Column(name = "OBSERVACIONES", length = 400)
    private String observaciones;

    @Column(name = "FACTURA_PATH", length = 300)
    private String facturaPath;

    @Column(name = "GUIA_REMISION_PATH", length = 300)
    private String guiaRemisionPath;

    @Column(name = "PICKING_PATH", length = 300)
    private String pickingPath;


}