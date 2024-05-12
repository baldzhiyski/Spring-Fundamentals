package org.softuni.mobilele.web;

import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register(Model model){
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String registerUser(UserRegisterDto userRegisterDto){

        // TODO : Validation

        this.userService.registerUser(userRegisterDto);

        return "redirect:/users/login";
    }

    @GetMapping("/users/login")
    public String logIn(Model model){
        return "auth-login";
    }


}
