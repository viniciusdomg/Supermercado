package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import service.ItemCustomService;

@Controller
public class AdminController {

    @Autowired
    ItemCustomService customService;
}
