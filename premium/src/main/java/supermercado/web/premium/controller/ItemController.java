package supermercado.web.premium.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import supermercado.web.premium.service.ItemCustomService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ItemController {

    @Autowired
    private ItemCustomService itemService;

    @GetMapping("/index")
    public String item(Model model, HttpServletResponse response) {

        Cookie visitCookie = new Cookie("visita", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        visitCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(visitCookie);

        return "principal";
    }
}
