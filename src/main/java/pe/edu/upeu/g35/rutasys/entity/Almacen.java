package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ALMACEN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALMACEN")
    @SequenceGenerator(name = "SEQ_ALMACEN", sequenceName = "SEQ_ALMACEN", allocationSize = 1)
    @Column(name = "ID_ALMACEN")
    private Long id;

    @Column(name = "NOMBRE_ALMACEN", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DIRECCION_ALMACEN", nullable = false, length = 200)
    private String direccion;

    @Column(name = "PUERTAS_DISPONIBLES_ALMACEN", nullable = false)
    private Integer puertasDisponibles;

    @Column(name = "ESTADO_ALMACEN", nullable = false, length = 20)
    private String estado;
}