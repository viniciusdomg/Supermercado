package supermercado.web.premium.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supermercado.web.premium.domain.Item;
import supermercado.web.premium.domain.Usuario;
import supermercado.web.premium.service.ItemCustomService;
import supermercado.web.premium.service.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@Controller
@RequestMapping("/publico")
public class ClienteController {

    @Autowired
    ItemCustomService customService;
    @Autowired
    UserDetailsServiceImpl userService;

    @GetMapping(value = "/produtos")
    public String carregaItens(Model model, HttpServletResponse response, HttpSession session){
        model.addAttribute("itens", customService.itensValidos());
        Map<Item, Integer> carrinho = (Map<Item, Integer>) session.getAttribute("carrinho");
        int quantidadeTotal = 0;
        if (carrinho != null) {
            for (Integer quantidade : carrinho.values()) {
                quantidadeTotal += quantidade;
            }
        }
        model.addAttribute("quantidadeCarrinho", quantidadeTotal);
        Cookie visitaCookie = new Cookie("visita", String.valueOf(System.currentTimeMillis()));
        visitaCookie.setMaxAge(24 * 60 * 60);
        visitaCookie.setPath("/");
        response.addCookie(visitaCookie);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            try {
                Usuario usuario = userService.findByUsername(username);
                model.addAttribute("usuario", usuario);
            } catch (UsernameNotFoundException e) {
                model.addAttribute("usuario", null);
            }
        } else {
            model.addAttribute("usuario", null);
        }
        return "principal";
    }

    @GetMapping(value = "/adicionarCarrinho/{id}")
    public String adicionarItemAoCarrinho(@PathVariable("id") Long id, HttpSession session) {
        Item item = customService.searchItemById(id);
        if (item != null) {
            Map<Item, Integer> carrinho = (Map<Item, Integer>) session.getAttribute("carrinho");
            if (carrinho == null) {
                carrinho = new HashMap<>();
            }

            int quantidadeAtual = carrinho.getOrDefault(item, 0);
            carrinho.put(item, quantidadeAtual + 1);

            session.setAttribute("carrinho", carrinho);
        }
        return "redirect:/publico/produtos";
    }
    @GetMapping(value = "/carrinho")
    public String carregaCarrinho(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Map<Item, Integer> carrinho = (Map<Item, Integer>) session.getAttribute("carrinho");
        if (carrinho == null || carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Nenhum item no carrinho.");
            return "redirect:/publico/produtos";
        }
        model.addAttribute("carrinho", carrinho);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            try {
                Usuario usuario = userService.findByUsername(username);
                model.addAttribute("usuario", usuario);
            } catch (UsernameNotFoundException e) {
                model.addAttribute("usuario", null);
            }
        } else {
            model.addAttribute("usuario", null);
        }
        return "Carrinho";
    }
    @GetMapping(value = "/removerCarrinho/{id}")
    public String removerItemDoCarrinho(@PathVariable("id") Long id, HttpSession session) {
        Item item = customService.searchItemById(id);
        if (item != null) {
            Map<Item, Integer> carrinho = (Map<Item, Integer>) session.getAttribute("carrinho");
            if (carrinho != null && carrinho.containsKey(item)) {
                int quantidadeAtual = carrinho.get(item);
                if (quantidadeAtual > 1) {
                    carrinho.put(item, quantidadeAtual - 1);
                } else {
                    carrinho.remove(item);
                }
                session.setAttribute("carrinho", carrinho);
            }
        }
        return "redirect:/publico/carrinho";
    }

    @GetMapping(value = "/finalizarCompra")
    public String finalizaCompra(HttpSession session){
        session.removeAttribute("carrinho");
        return "redirect:/publico/produtos";
    }
}
