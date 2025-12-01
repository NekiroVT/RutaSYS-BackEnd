package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ADMINISTRADOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMINISTRADOR")
    @SequenceGenerator(name = "SEQ_ADMINISTRADOR", sequenceName = "SEQ_ADMINISTRADOR", allocationSize = 1)
    @Column(name = "IDADMINISTRADOR")
    private Long id;

    @OneToOne(optional = false) // Generalmente un usuario es un único administrador.
    @JoinColumn(name = "IDUSUARIO", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "NOMBRE_COMPLETO", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "DNI", length = 8)
    private String dni;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "CARGO", length = 50)
    private String cargo; // Ej: Jefe de Logística, Asistente de Operaciones

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado; // ACTIVO / INACTIVO

    // ✅ CAMBIO: Añadido campo para la URL de la imagen del Administrador
    @Column(name = "IMAGEN_URL", length = 255)
    private String imagenUrl;
}