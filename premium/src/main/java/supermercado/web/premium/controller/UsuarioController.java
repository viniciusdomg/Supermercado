
package supermercado.web.premium.controller;

import supermercado.web.premium.domain.Usuario;
import supermercado.web.premium.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cadusuario")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("CadastroUsuario");
    }

    @PostMapping("/salvarUsuario")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam(defaultValue = "false") boolean isAdmin) {
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setIsAdmin(isAdmin);
        userRepository.save(user);
        return "redirect:/Login";
    }

}
