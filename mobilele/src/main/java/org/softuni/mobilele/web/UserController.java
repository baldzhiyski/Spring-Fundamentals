package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register(Model model){
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "auth-register";
    }

    @PostMapping("/users/register")
    public ModelAndView registerUser(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (this.userService.userByUsernameExists(userRegisterDto.getUsername())) {
            bindingResult.rejectValue("username", "error.userRegisterDto", "Username already exists.");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("auth-register");
            modelAndView.addObject("userRegisterDto", userRegisterDto);
        } else {
            try {
                userService.registerUser(userRegisterDto);
                modelAndView.setViewName("redirect:/users/login");
            } catch (Exception e) {
                e.printStackTrace();
                bindingResult.rejectValue("photo", "error.userRegisterDto", "Failed to upload photo.");
                modelAndView.setViewName("auth-register");
                modelAndView.addObject("userRegisterDto", userRegisterDto);
            }
        }
        return modelAndView;
    }

    @GetMapping("/users/login")
    public String logIn(Model model){
        return "auth-login";
    }


}
