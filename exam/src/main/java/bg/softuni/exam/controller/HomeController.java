package bg.softuni.exam.controller;

import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("currentUser")
    public CurrentLoggedUser currentLoggedUser() {
        return this.userService.getLoggedUser();
    }

    @GetMapping("/")
    public String indexPage(Model model){
        if(userService.getLoggedUser().isLogged()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        if(!userService.getLoggedUser().isLogged()){
            return "redirect:/";
        }
        return "home";
    }
}
