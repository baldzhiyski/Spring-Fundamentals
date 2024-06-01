package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.domain.dtos.offer.OfferRegisterDto;
import org.softuni.mobilele.domain.entities.Model;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.services.BrandService;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class OffersController {

    private OfferService offerService;
    private UserService userService;

    private BrandService brandService;

    @Autowired
    public OffersController(OfferService offerService, UserService userService, BrandService brandService) {
        this.offerService = offerService;
        this.userService = userService;
        this.brandService = brandService;
    }
    @ModelAttribute("loggedIn")
    public boolean isLoggedIn() {
        return userService.checkLoggedUser();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return userService.isAdmin();
    }

    @GetMapping("/offers/all")
    public ModelAndView getOffers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("offers");

        List<Offer> offers = offerService.getAllOffers(); // Fetch offers from the service layer
        modelAndView.addObject("offers", offers); // Pass offers data to the view


        return modelAndView;
    }

    @GetMapping("/offers/add")
    public ModelAndView addOfferPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("offerRegisterDto", new OfferRegisterDto());


        modelAndView.setViewName("offer-add");
        return modelAndView;
    }

    @ModelAttribute("brands")
    public List<String> getBrandsName(){
       return this.brandService.getAllBrandsNames();
    }

    @PostMapping("/offers/add")
    public ModelAndView addOffer(@Valid OfferRegisterDto offerRegisterDto,
                                 BindingResult bindingResult,
                                 @RequestParam("photo") MultipartFile photo) {

        ModelAndView modelAndView = new ModelAndView();

        // Custom validation: Check if photo is not provided
        if (photo.isEmpty()) {
            bindingResult.rejectValue("photo", "error.offerRegisterDto", "Please select a photo.");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("offer-add"); // Return to the same view
            modelAndView.addObject("offerRegisterDto", offerRegisterDto);

        } else {
            try {
                modelAndView.setViewName("redirect:/home"); // Redirect to home page
                this.offerService.addOffer(offerRegisterDto);
            } catch (Exception e) {
                // Handle the exception if needed
                bindingResult.rejectValue("photo", "error.offerRegisterDto", "Failed to upload photo.");
                modelAndView.setViewName("offer-add"); // Return to the same view with the error message
                modelAndView.addObject("offerRegisterDto", offerRegisterDto);
            }

        }
        return modelAndView;
    }
}
