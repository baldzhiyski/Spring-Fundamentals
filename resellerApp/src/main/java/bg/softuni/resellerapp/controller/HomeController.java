package bg.softuni.resellerapp.controller;

import bg.softuni.resellerapp.model.dtos.LoggedUserOffersDto;
import bg.softuni.resellerapp.service.OfferService;
import bg.softuni.resellerapp.service.UserService;
import bg.softuni.resellerapp.util.CurrentLoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private UserService userService;
    private OfferService offerService;

    public HomeController(UserService userService, OfferService offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @ModelAttribute("isLogged")
    public boolean isCurrentUserLogged() {
        return userService.isLogged();
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        if (this.userService.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(Model model) {
        if (!this.userService.isLogged()) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("loggedUserOffersDto", offerService.getInfoLoggedUser());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
