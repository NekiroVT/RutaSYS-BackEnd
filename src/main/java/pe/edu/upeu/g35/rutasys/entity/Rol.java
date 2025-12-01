package pe.edu.upeu.g35.rutasys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ROL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROL")
    @SequenceGenerator(name = "SEQ_ROL", sequenceName = "SEQ_ROL", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String nombre;

    @JsonIgnore// ⬅️ RELACIÓN A PERMISOS (Para saber qué permisos tiene este rol)
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolPermiso> rolPermisos;

    @JsonIgnore // ⬅️ RELACIÓN A USUARIOS (Para saber qué usuarios tienen este rol, si usas la tabla de unión)
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolUsuario> rolUsuarios;
}