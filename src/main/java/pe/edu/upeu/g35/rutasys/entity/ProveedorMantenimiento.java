package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROVEEDOR_MANTENIMIENTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorMantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROV_MANTENIMIENTO")
    @SequenceGenerator(name = "SEQ_PROV_MANTENIMIENTO", sequenceName = "SEQ_PROV_MANTENIMIENTO", allocationSize = 1)
    @Column(name = "ID_PROVEEDOR")
    private Long id;

    @Column(name = "NOMBRE_PROVEEDOR", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CONTACTO_PROVEEDOR", nullable = false, length = 100)
    private String contacto;

    @Column(name = "TELEFONO_PROVEEDOR", nullable = false, length = 15)
    private String telefono;

    @Column(name = "EMAIL_PROVEEDOR", nullable = false, length = 100)
    private String email;

    @Column(name = "ESTADO_PROVEEDOR", nullable = false, length = 20)
    private String estado;
}