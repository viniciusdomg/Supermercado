package supermercado.web.premium.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supermercado.web.premium.repository.UsuarioRepository;

@Service
public class UsuarioCustomService {

    @Autowired
    UsuarioRepository repository;
}
