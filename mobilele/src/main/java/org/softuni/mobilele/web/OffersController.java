package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.domain.dtos.offer.OfferRegisterDto;
import org.softuni.mobilele.domain.entities.Model;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        modelAndView.addObject("offerRegisterDto", new OfferRegisterDto());
        boolean loggedIn = userService.checkLoggedUser();
        modelAndView.addObject("loggedIn", loggedIn);

        boolean isAdmin = userService.isAdmin();
        modelAndView.addObject("isAdmin", isAdmin);

        modelAndView.setViewName("offer-add");
        return modelAndView;
    }

    @PostMapping("offers/add")
    public ModelAndView addOffer(@Valid OfferRegisterDto offerRegisterDto,
                                 BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("offerRegisterDto", offerRegisterDto);
            modelAndView.setViewName("offer-add"); // Return to the same view
        } else {
            modelAndView.setViewName("redirect:/home"); // Redirect to home page
            // Add logic to add the offer
        }
        return modelAndView;
    }


}
