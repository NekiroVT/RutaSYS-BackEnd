package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHOFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chofer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHOFER")
    @SequenceGenerator(name = "SEQ_CHOFER", sequenceName = "SEQ_CHOFER", allocationSize = 1)
    @Column(name = "IDCHOFER")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO")
    private Usuario usuario;

    @Column(name = "NOMBRE_COMPLETO", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "DNI", length = 8)
    private String dni;

    @Column(name = "LICENCIA", length = 20)
    private String licencia;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "CORREO", length = 120)
    private String correo;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}