package bg.softuni.exam.controller;

import bg.softuni.exam.model.dto.user.LoggedUserWithDetailsDto;
import bg.softuni.exam.service.PaintingService;
import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    private UserService userService;
    private PaintingService paintingService;

    public HomeController(UserService userService, PaintingService paintingService) {
        this.userService = userService;
        this.paintingService = paintingService;
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
        LoggedUserWithDetailsDto loggedUserWithDetailsDto = new LoggedUserWithDetailsDto();
        loggedUserWithDetailsDto.setWrapperPaintings(this.paintingService.getWrapper());
        loggedUserWithDetailsDto.setUsername(this.userService.getLoggedUser().getUsername());

        if(!model.containsAttribute("loggedUserWithDetails")){
            model.addAttribute("loggedUserWithDetails",loggedUserWithDetailsDto);
        }
        return "home";
    }
}
