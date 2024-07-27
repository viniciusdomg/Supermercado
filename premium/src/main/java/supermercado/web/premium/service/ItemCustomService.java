package supermercado.web.premium.service;

import supermercado.web.premium.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supermercado.web.premium.repository.ItemRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemCustomService {

    @Autowired
    private ItemRepository repository;

    public List<Item> listaItens (){
        return repository.itensDeleted();
    }

    public List<Item> itensValidos(){
        return repository.itensNotDeleted();
    }

    public Item searchItemById(Long id){
        return repository.searchById(id);
    }

    @Transactional
    public Item saveItem(Item item){
        if(item.getCodigo()!=null) {
            item.setCodigo(repository.getNextInterval());
        }
        return repository.save(item);
    }

    @Transactional
    public Item removeItem(Long id){
        Item item = repository.searchById(id);
        item.setIsDeleted(LocalDateTime.now());
        return repository.save(item);
    }

}
