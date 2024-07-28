
package supermercado.web.premium.service;

import supermercado.web.premium.domain.Usuario;
import supermercado.web.premium.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import supermercado.web.premium.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public Usuario findByUsername(String username){
        return repository.findUsuarioByUsername(username);
    }

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findUsuarioByUsername(username);
        if (usuario != null) {
            System.out.println("Usuário encontrado: " + usuario.getUsername());
            return org.springframework.security.core.userdetails.User.withUsername(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.getIsAdmin() ? "ADMIN" : "USER")
                    .build();
        }
        System.out.println("Usuário não encontrado: " + username);
        throw new UsernameNotFoundException("User not found");
    }

}
