package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "PERMISO")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISO")
    @SequenceGenerator(name = "SEQ_PERMISO", sequenceName = "SEQ_PERMISO", allocationSize = 1)
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false, unique = true) // ⬅️ Nombre debe ser único
    private String nombre;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    // ⬅️ RELACIÓN INVERSA a la tabla de unión RolPermiso (Para saber qué roles usan este permiso)
    @OneToMany(mappedBy = "permiso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolPermiso> rolPermisos;
}