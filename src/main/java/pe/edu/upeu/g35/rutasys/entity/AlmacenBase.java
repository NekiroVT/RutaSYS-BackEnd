package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ALMACEN_BASE") // Cambiado a ALMACEN_BASE
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenBase { // Renombrado a AlmacenBase

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALMACEN_BASE")
    @SequenceGenerator(name = "SEQ_ALMACEN_BASE", sequenceName = "SEQ_ALMACEN_BASE", allocationSize = 1)
    @Column(name = "ID_ALMACEN_BASE") // Cambiado a ID_ALMACEN_BASE
    private Long id;

    @Column(name = "NOMBRE_ALMACEN_BASE", nullable = false, length = 100) // Cambiado a NOMBRE_ALMACEN_BASE
    private String nombre;

    @Column(name = "DIRECCION_ALMACEN_BASE", nullable = false, length = 200) // Cambiado a DIRECCION_ALMACEN_BASE
    private String direccion;

    @Column(name = "PUERTAS_DISPONIBLES_ALMACEN_BASE", nullable = false) // Cambiado a PUERTAS_DISPONIBLES_ALMACEN_BASE
    private Integer puertasDisponibles;

    @Column(name = "ESTADO_ALMACEN_BASE", nullable = false, length = 20) // Cambiado a ESTADO_ALMACEN_BASE
    private String estado;
}