package org.softuni.mobilele.web;

import org.softuni.mobilele.domain.entities.Model;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OffersController {

    private OfferService offerService;
    private UserService userService;

    @Autowired
    public OffersController(OfferService offerService, UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping("/offers/all")
    public ModelAndView getOffers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("offers");

        List<Offer> offers = offerService.getAllOffers(); // Fetch offers from the service layer
        modelAndView.addObject("offers", offers); // Pass offers data to the view

        boolean loggedIn = userService.checkLoggedUser();
        modelAndView.addObject("loggedIn", loggedIn);

        boolean isAdmin = userService.isAdmin();
        modelAndView.addObject("isAdmin", isAdmin);

        return modelAndView;
    }

    @GetMapping("/offers/add")
    public ModelAndView addOfferPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("offer-add");
        return modelAndView;
    }


}
