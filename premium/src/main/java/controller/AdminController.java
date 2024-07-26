package controller;

import domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ItemCustomService;

import java.util.List;

/*Implemente a rota de (“/admin”) para, a partir de uma solicitação do tipo GET, gerar uma resposta
contendo no corpo um HTML que contém uma tabela de todos os itens (linhas) que estão presentes
no banco de dados e que não estão deletados (isDeleted == null). Para cada item listado adicione um
link para a rota “/editar” e “/deletar” passando como parâmetro para tal rota o ID do item escolhido.
Por fim, adicione na página gerada pela rota “/admin” um link para a rota “/cadastro”. (1,0 pontos)
*/

@Controller
public class AdminController {

    @Autowired
    ItemCustomService customService;
}
