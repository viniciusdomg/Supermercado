package supermercado.web.premium.repository;

import supermercado.web.premium.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository <Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.id = ?1")
    Item searchById(Long id);

    @Query(value = "SELECT nextval('item_sequence')", nativeQuery = true)
    Long getNextInterval();
}
