package org.softuni.pathfinder.web;

import org.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavBarController {
    private UserService userService;

    @Autowired
    public NavBarController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String firstPage(Model model) {
        boolean loggedIn = this.userService.isLoggedIn();
        model.addAttribute("loggedIn", loggedIn);

        boolean isAdmin = this.userService.isAdmin();
        model.addAttribute("isAdmin", isAdmin);
        return "index";
    }
    @GetMapping("/home")
    public String home(Model model) {
        boolean loggedIn = this.userService.isLoggedIn();
        model.addAttribute("loggedIn", loggedIn);


        boolean isAdmin = this.userService.isAdmin();
        model.addAttribute("isAdmin", isAdmin);

        return "index";
    }


    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/login");

        this.userService.logOut();

        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView about(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/about");

        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);


        boolean isAdmin = this.userService.isAdmin();
        modelAndView.addObject("isAdmin", isAdmin);

        modelAndView.addObject("isInThePage",true);

        return modelAndView;

    }
}
