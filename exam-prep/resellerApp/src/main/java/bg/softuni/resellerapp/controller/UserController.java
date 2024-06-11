package bg.softuni.resellerapp.controller;

import bg.softuni.resellerapp.model.dtos.user.UserLogInDto;
import bg.softuni.resellerapp.model.dtos.user.UserRegisterDto;
import bg.softuni.resellerapp.service.UserService;
import jakarta.validation.Valid;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String getRegisterPage(Model model){
        if(userService.isLogged()){
            return "redirect:/home";
        }
        if(!model.containsAttribute("registerDto")){
            model.addAttribute("registerDto",new UserRegisterDto());
        }

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid UserRegisterDto registerDto, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDto", bindingResult);
            redirectAttributes.addFlashAttribute("registerDto", registerDto);
            modelAndView.setViewName("redirect:/register");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/login");
        this.userService.register(registerDto);
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogInPage(Model model){
        if(this.userService.isLogged()){
            return "redirect:/home";
        }
        if(!model.containsAttribute("logInDto")){
            model.addAttribute("logInDto",new UserLogInDto());
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView logInUser( @Valid UserLogInDto logInDto,BindingResult bindingResult,RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();

        boolean success = this.userService.logIn(logInDto);
        if(bindingResult.hasErrors() || !success){
            redirectAttributes.addFlashAttribute("badRequest", "Invalid username or password. Please try again.");
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
