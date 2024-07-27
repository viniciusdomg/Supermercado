package supermercado.web.premium.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supermercado.web.premium.service.ItemCustomService;

@Controller
@RequestMapping("/publico")
public class ClienteController {

    @Autowired
    ItemCustomService customService;

    @GetMapping(value = "/produtos")
    public String carregaItens(Model model){
        model.addAttribute("itens", customService.itensValidos());
        return "principal";
    }
}
