package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ItemRepository;

@Service
public class ItemCustomService {

    @Autowired
    private ItemRepository repository;
}
