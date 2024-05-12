package org.softuni.mobilele.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/users/register")
    public String register(Model model){
        return "auth-register";
    }
}
