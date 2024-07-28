package supermercado.web.premium.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/login")
    public String getLoginPage(){
        return "Login";
    }
    @GetMapping("/logout")
    public String getLogoutPage(HttpSession session){
        session.invalidate();
        return "Logout";
    }
}