package domain;

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

    @Column(name = "nome", nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String nome;

    @Column(name = "descricao", nullable = true)
    private String descricao;

    @Column(name = "preco", nullable = false)
    @NotNull
    private BigDecimal preco;

    @Column(name = "imageUrl", nullable = false)
    @NotNull
    private String imageUrl;

    @Column(name = "isDeleted", nullable = true)
    private LocalDateTime isDeleted;

    public Item() {}

    public Item (Long idItem){
        this.id = idItem;
    }

}
