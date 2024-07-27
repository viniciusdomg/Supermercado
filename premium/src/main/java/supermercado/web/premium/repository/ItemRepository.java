package supermercado.web.premium.repository;

import supermercado.web.premium.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository <Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.id = ?1")
    Item searchById(Long id);

    @Query(value = "SELECT nextval('supermercado.codigo_seq')", nativeQuery = true)
    Long getNextInterval();

    @Query("SELECT i FROM Item i WHERE i.isDeleted IS NULL")
    List<Item> itensNotDeleted();
}
