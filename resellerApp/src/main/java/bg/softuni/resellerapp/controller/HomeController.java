package bg.softuni.resellerapp.controller;

import bg.softuni.resellerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("isLogged")
    public boolean isCurrentUserLogged(){
        return userService.isLogged();
    }
    @GetMapping("/")
    public String getIndex(Model model){
        if(this.userService.isLogged()){
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(Model model){
        if(!this.userService.isLogged()){
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("home");
    }
}
