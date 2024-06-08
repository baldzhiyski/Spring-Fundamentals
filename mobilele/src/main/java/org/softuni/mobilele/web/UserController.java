package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.softuni.mobilele.domain.dtos.user.UserLogInDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.domain.entities.User;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public String register(Model model){
        if(!model.containsAttribute("userRegisterDto")){
            model.addAttribute("userRegisterDto", new UserRegisterDto());
        }
        return "auth-register";
    }

    @PostMapping("/users/register")
    public ModelAndView registerUser(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult, @RequestParam("photo") MultipartFile photo,
                                     RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            modelAndView.setViewName("redirect:/users/register"); // Redirect back to the registration page
        } else {
            try {
                userService.registerUser(userRegisterDto);
                modelAndView.setViewName("redirect:/users/login"); // Redirect to the login page
            } catch (Exception e) {
                bindingResult.rejectValue("photo", "error.userRegisterDto", "Failed to upload photo.");
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
                redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
                modelAndView.setViewName("redirect:/users/register"); // Redirect back to the registration page with the error message
            }
        }
        return modelAndView;
    }

    @GetMapping("/users/login")
    public String logIn(Model model){
        if(!model.containsAttribute("logInDto")){
            model.addAttribute("logInDto", new UserLogInDto());
        }
        return "auth-login";
    }

    @PostMapping("/users/login")
    public ModelAndView logIntoAccount(@Valid UserLogInDto logInDto,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.logInDto", bindingResult);
            redirectAttributes.addFlashAttribute("logInDto", logInDto);
            modelAndView.setViewName("redirect:/users/login");
            return modelAndView;
        }

        // Redirect to home page
        modelAndView.setViewName("redirect:/home");
        this.userService.logIn(logInDto);
        return modelAndView;
    }

    @GetMapping("/users/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        if(!this.userService.checkLoggedUser()){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/users/login");

        this.userService.logOut();

        return modelAndView;
    }


}
