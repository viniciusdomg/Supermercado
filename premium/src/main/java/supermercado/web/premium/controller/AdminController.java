package supermercado.web.premium.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import supermercado.web.premium.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import supermercado.web.premium.service.*;
import supermercado.web.premium.service.arquivos.FileStorageService;

import java.util.List;

/*Implemente a rota de (“/admin”) para, a partir de uma solicitação do tipo GET, gerar uma resposta
contendo no corpo um HTML que contém uma tabela de todos os itens (linhas) que estão presentes
no banco de dados e que não estão deletados (isDeleted == null). Para cada item listado adicione um
link para a rota “/editar” e “/deletar” passando como parâmetro para tal rota o ID do item escolhido.
Por fim, adicione na página gerada pela rota “/admin” um link para a rota “/cadastro”. (1,0 pontos)
*/

@Controller
@RequestMapping("/interno")
public class AdminController {

    @Autowired
    ItemCustomService customService;
    @Autowired
    FileStorageService fileService;

    @GetMapping(value = "/produtos")
    public String carregaProdutos(Model model){
        model.addAttribute("itens", customService.itensValidos());
       return "ListaProdutos";
    }

    /**
     * Mostra a tela que contém um formulário para cadastro de um item, carregando os elementos do
     * formuláro html
     * */
    @GetMapping(value = "/cadastro")
    public String carregaCadastro(Model model){
        Item item =  new Item();
        model.addAttribute("item", item);
        return "CadastraProduto";
    }

    @PostMapping(value = "/salvar")
    public ModelAndView salvarItem(@ModelAttribute Item item, @RequestParam("file") MultipartFile file){
        item.setImageUrl("/images/"+file.getOriginalFilename());
        fileService.save(file);
        customService.saveItem(item);
        ModelAndView modelAndView = new ModelAndView("ListaProdutos");
        modelAndView.addObject("itens", customService.listaItens());
        return modelAndView;
    }

    /**
     * Mostra a tela com os itens deletados, quando isDeleted possui uma data
     * @param model
     * @return
     */
    @GetMapping(value = "/deletados")
    public String carregaDeletados(Model model){
        model.addAttribute("itens", customService.listaItens());
        return "ItensDeletados";
    }

    @GetMapping(value = "/editar/{id}")
    public String carregaEdit(@PathVariable("id") Long id, Model model){
        Item item = customService.searchItemById(id);
        model.addAttribute("item", item);
        return "EditarProduto";
    }

    @GetMapping(value = "/deletar/{id}")
    public String carregaDelete(@PathVariable("id") Long id, Model model){
        model.addAttribute("item", customService.searchItemById(id));
        return "ListaProdutos";
    }
}
