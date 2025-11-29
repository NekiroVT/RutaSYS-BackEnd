package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENTREGA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTREGA")
    @SequenceGenerator(name = "SEQ_ENTREGA", sequenceName = "SEQ_ENTREGA", allocationSize = 1)
    @Column(name = "ID_ENTREGA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_MANIFIESTO", nullable = false)
    private Manifiesto manifiesto;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @Column(name = "VENTANA_HORARIA_INICIO_ENTREGA")
    private LocalDateTime ventanaHorariaInicio;

    @Column(name = "VENTANA_HORARIA_FIN_ENTREGA")
    private LocalDateTime ventanaHorariaFin;

    @Column(name = "ESTADO_ENTREGA", nullable = false, length = 20)
    private String estado;

    @Column(name = "OBSERVACION_FINAL_ENTREGA", length = 500)
    private String observacionFinal;
}