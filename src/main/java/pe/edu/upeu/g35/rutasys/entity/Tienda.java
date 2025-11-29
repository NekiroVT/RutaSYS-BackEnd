package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIENDA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIENDA")
    @SequenceGenerator(name = "SEQ_TIENDA", sequenceName = "SEQ_TIENDA", allocationSize = 1)
    @Column(name = "IDTIENDA")
    private Long id;

    @Column(name = "NOMBRE_TIENDA", nullable = false, length = 150)
    private String nombreTienda;

    @Column(name = "DISTRITO", length = 100)
    private String distrito;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "CANAL", length = 50)
    private String canal;

    @Column(name = "DESTINATARIO", length = 150)
    private String destinatario;

    @Column(name = "VH_INICIO", length = 5)
    private String vhInicio;

    @Column(name = "VH_FIN", length = 5)
    private String vhFin;

    @Column(name = "NUMERO_PRINCIPAL", length = 20)
    private String numeroPrincipal;

    @Column(name = "NUMERO_OPCIONAL", length = 20)
    private String numeroOpcional;

    @Column(name = "EMAIL_TIENDA", length = 150)
    private String emailTienda;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}