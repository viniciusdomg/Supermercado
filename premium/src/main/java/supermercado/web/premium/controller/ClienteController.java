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
import supermercado.web.premium.service.ItemCustomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/publico")
public class ClienteController {

    @Autowired
    ItemCustomService customService;

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
        session.invalidate();
        return "redirect:/publico/produtos";
    }
}
