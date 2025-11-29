package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    @Column(name = "IDCLIENTE")
    private Long id;

    @Column(name = "RUC_CLIENTE", nullable = false, length = 11)
    private String ruc;

    @Column(name = "RAZON_SOCIAL", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "CONTACTO", length = 100)
    private String contacto;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "CORREO", length = 120)
    private String correo;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}