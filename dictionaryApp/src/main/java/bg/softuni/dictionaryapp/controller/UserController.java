package bg.softuni.dictionaryapp.controller;

import bg.softuni.dictionaryapp.model.dtos.user.LogInDto;
import bg.softuni.dictionaryapp.model.dtos.user.UserRegisterDto;
import bg.softuni.dictionaryapp.service.UserService;
import bg.softuni.dictionaryapp.util.CurrentLoggedInUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private CurrentLoggedInUser currentLoggedInUser;

    private UserService userService;

    public UserController(CurrentLoggedInUser currentLoggedInUser, UserService userService) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.userService = userService;
    }

    @ModelAttribute("loggedInUser")
    public CurrentLoggedInUser getLoggedUser(){
        return currentLoggedInUser;
    }

    @GetMapping("/user/register")
    public String registerPage(Model model){
        if(this.currentLoggedInUser.isLogged()){
            return "redirect:/home";
        }
        if(!model.containsAttribute("userRegisterDto")){
            model.addAttribute("userRegisterDto",new UserRegisterDto());
        }
        return "register";
    }

    @PostMapping("/user/register")
    public ModelAndView registerUser(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult , RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            modelAndView.setViewName("redirect:/user/register");
            return modelAndView;
        }
        userService.registerUser(userRegisterDto);
        modelAndView.setViewName("redirect:/user/login");
        return modelAndView;
    }

    @GetMapping("/user/login")
    public String logInPage(Model model){
        if(this.currentLoggedInUser.isLogged()){
            return "redirect:/home";
        }
        if(!model.containsAttribute("logInDto")){
            model.addAttribute("logInDto",new LogInDto());
        }
        return "login";
    }

    @PostMapping("/user/login")
    public ModelAndView logInUser(@Valid LogInDto logInDto,BindingResult bindingResult,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("incorrect",true);
            modelAndView.setViewName("redirect:/user/login");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/home");
        this.userService.logIn(logInDto);
        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logOut(){
        if(!this.currentLoggedInUser.isLogged()){
            return  new ModelAndView("redirect:/home");
        }
        this.userService.logOutCurrentUser();
        return new ModelAndView("redirect:/");
    }
}
