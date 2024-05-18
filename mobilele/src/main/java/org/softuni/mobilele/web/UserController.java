package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.softuni.mobilele.domain.dtos.UserLogInDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.domain.entities.User;
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
        model.addAttribute("logInDto", new UserLogInDto());
        return "auth-login";
    }

    @PostMapping("/users/login")
    public ModelAndView logIntoAccount(@Valid UserLogInDto logInDto,
                                       BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("logInDto",logInDto);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("auth-login");
            modelAndView.addObject("loginError", "Invalid login details");
            return modelAndView;
        }

        if (!this.userService.userByUsernameExists(logInDto.getUsername())) {
            modelAndView.setViewName("auth-login");
            modelAndView.addObject("loginError", "No such username in the database. Please create an account first.");
            return modelAndView;
        }

        User user = this.userService.getUserByUsername(logInDto.getUsername());
        boolean areEquals = BCrypt.checkpw(logInDto.getPassword(), user.getPassword());

        if (areEquals) {
            
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("auth-login");
            modelAndView.addObject("loginError", "Incorrect username or password");
            return modelAndView;
        }

        return modelAndView;
    }


}
