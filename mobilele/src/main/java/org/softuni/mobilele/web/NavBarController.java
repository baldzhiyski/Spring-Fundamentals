package org.softuni.mobilele.web;

import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavBarController {

    private UserService userService;

    @Autowired
    public NavBarController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        boolean loggedIn = userService.checkLoggedUser();
        modelAndView.addObject("loggedIn", loggedIn);

        boolean isAdmin = userService.isAdmin();
        modelAndView.addObject("isAdmin", isAdmin);

        return modelAndView;
    }
}


