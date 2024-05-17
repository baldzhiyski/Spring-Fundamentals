package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String showRegistrationForm(Model model) {
        // Create a new UserRegisterDto object and add it to the model
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/users/register")
    public ModelAndView registerConfirm(@Valid UserRegisterDto userRegisterDto,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        // Check if password matches confirm password
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.user", "Passwords do not match.");
        }

        // Check if user with the same email exists
        if (userService.usernameByEmailExists(userRegisterDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "User with this email already exists.");
        }

        // Check if user with the same username exists
        if (userService.userByUsernameExists(userRegisterDto.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "User with this username already exists.");
        }

        // If there are any validation errors, return to the registration page with error messages
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            // Otherwise, proceed with user registration
            // Your registration logic here
            modelAndView.setViewName("redirect:/users/login");
            this.userService.register(userRegisterDto);
            // Redirect to login page after successful registration
        }

        return modelAndView;
    }

    @GetMapping("/users/login")
    public ModelAndView getLoginPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("login");

        return modelAndView;
    }
}
