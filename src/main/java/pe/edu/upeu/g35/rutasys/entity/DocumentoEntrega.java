package pe.edu.upeu.g35.rutasys.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DOCUMENTO_ENTREGA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOC_ENTREGA")
    @SequenceGenerator(name = "SEQ_DOC_ENTREGA", sequenceName = "SEQ_DOC_ENTREGA", allocationSize = 1)
    @Column(name = "ID_DOCUMENTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTREGA", nullable = false)
    private Entrega entrega;

    @Column(name = "TIPO_DOCUMENTO", nullable = false, length = 50)
    private String tipoDocumento;

    @Column(name = "URL_FIRMA_DOCUMENTO", length = 255)
    private String urlFirma;

    @Column(name = "ESTADO_DOCUMENTO", nullable = false, length = 20)
    private String estado;
}