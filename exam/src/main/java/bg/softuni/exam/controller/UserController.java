package bg.softuni.exam.controller;

import bg.softuni.exam.model.dto.LogInDto;
import bg.softuni.exam.model.dto.RegisterDto;
import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute("currentUser")
    public CurrentLoggedUser currentLoggedUser() {
        return this.userService.getLoggedUser();
    }
    @GetMapping("/register")
    public String registerPage(Model model){
        if(!model.containsAttribute("registerDto")){
            model.addAttribute("registerDto",new RegisterDto());

        }
        if(this.userService.getLoggedUser().isLogged()){
            return "redirect:/home";
        }

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid RegisterDto userRegisterDto, BindingResult bindingResult , RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDto", bindingResult);
            redirectAttributes.addFlashAttribute("registerDto", userRegisterDto);
            modelAndView.setViewName("redirect:/users/register");
            return modelAndView;
        }
        userService.registerUser(userRegisterDto);
        modelAndView.setViewName("redirect:/users/login");
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogInPage(Model model){
        if(!model.containsAttribute("logInUserDto")){
            model.addAttribute("logInUserDto",new LogInDto());
        }
        if(this.userService.getLoggedUser().isLogged()){
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView logInUser(@Valid LogInDto logInDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.logInUserDto", bindingResult);
            redirectAttributes.addFlashAttribute("logInUserDto", logInDto);
            modelAndView.setViewName("redirect:/users/login");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/home");
        this.userService.logIn(logInDto);
        return modelAndView;
    }


    @PostMapping("/logout")
    public ModelAndView logOut(){
        if(!this.userService.getLoggedUser().isLogged()){
            return  new ModelAndView("redirect:/home");
        }
        this.userService.logOutCurrentUser();
        return new ModelAndView("redirect:/");
    }
}
