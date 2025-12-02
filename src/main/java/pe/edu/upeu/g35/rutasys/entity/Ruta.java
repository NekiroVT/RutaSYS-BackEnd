package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "RUTA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RUTA")
    @SequenceGenerator(name = "SEQ_RUTA", sequenceName = "SEQ_RUTA", allocationSize = 1)
    @Column(name = "ID_RUTA")
    private Long id;

    @Column(name = "NOMBRE_RUTA", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION_RUTA", length = 255)
    private String descripcion;

    @Column(name = "DISTANCIA_APROX_KM_RUTA", precision = 5, scale = 2)
    private BigDecimal distanciaAproxKmRuta;

    @Column(name = "ESTADO_RUTA", nullable = false, length = 20)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_ALMACEN_ORIGEN", nullable = false)
    private AlmacenBase almacenOrigen;
}