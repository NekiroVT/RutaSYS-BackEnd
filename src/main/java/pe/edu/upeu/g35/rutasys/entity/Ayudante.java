package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AYUDANTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ayudante {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AYUDANTE")
    @SequenceGenerator(name = "SEQ_AYUDANTE", sequenceName = "SEQ_AYUDANTE", allocationSize = 1)
    @Column(name = "IDAYUDANTE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO")
    private Usuario usuario;

    @Column(name = "NOMBRE_COMPLETO", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "DNI", length = 8)
    private String dni;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "CORREO", length = 120)
    private String correo;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}