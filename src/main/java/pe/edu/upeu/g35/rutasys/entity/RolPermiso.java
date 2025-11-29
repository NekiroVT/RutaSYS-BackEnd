package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "rol_permiso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "permiso_id", referencedColumnName = "id")
    private Permiso permiso;
}
