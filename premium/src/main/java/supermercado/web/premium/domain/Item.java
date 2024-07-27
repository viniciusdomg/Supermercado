package supermercado.web.premium.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

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

    @Column(name = "codigo", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
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

    @Column (name = "data_de_validade", nullable = false)
    @NotNull (message = "É preciso adicionar a data de validade")
    private LocalDate dataDeValidade;

    @Column(name = "image_url", nullable = true, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "is_deleted", nullable = true)
    private Long isDeleted;

    public Item() {}

    public Item (Long idItem){
        this.id = idItem;
    }

    public void setCodigo(Long interval){
        String formattedInterval = String.format("%04d", interval);
        this.codigo = formattedInterval + LocalDateTime.now().getYear();
    }
    public void setIsDeleted(LocalDateTime data){
        if(data == null){
            this.isDeleted = null;
        }else {
            this.isDeleted = data.toEpochSecond(ZoneOffset.UTC);
        }
    }

    @Override
    public String toString(){
        return "Item: nome: "+this.nome +"descricao: "+ this.descricao+ "codigo: "+ this.codigo + "preço: " + this.preco;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
