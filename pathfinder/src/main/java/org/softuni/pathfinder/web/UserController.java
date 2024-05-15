package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @GetMapping("/users/register")
    public String register(Model model){
        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(@Valid UserRegisterDto userRegisterDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){



        return null;
    }
}
