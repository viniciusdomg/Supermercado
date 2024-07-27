package supermercado.web.premium.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( schema = "supermercado", name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String codigo;

    @Column(name = "nome", nullable = false, columnDefinition = "TEXT")
    @NotNull (message = "Preencher campo nome do item")
    @Size(min = 1, max = 100)
    private String nome;

    @Column (name = "quantidade", nullable = true)
    private Integer quantidade;

    @Column(name = "descricao", nullable = true, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "preco", nullable = false)
    @NotNull (message = "Preencher o campo preço do item")
    private BigDecimal preco;

    @Column(name = "imageurl", nullable = false, columnDefinition = "TEXT")
    @NotNull (message = "Adicionar uma imagem do item")
    private String imageUrl;

    @Column(name = "isdeleted", nullable = true)
    private LocalDateTime isDeleted;

    public Item() {}

    public Item (Long idItem){
        this.id = idItem;
    }

    public void setCodigo(Long interval){
        String formattedInterval = String.format("%04d", interval);
        this.codigo = formattedInterval + LocalDateTime.now().getYear();
    }

}
