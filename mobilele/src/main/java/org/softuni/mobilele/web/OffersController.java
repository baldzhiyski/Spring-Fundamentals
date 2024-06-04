package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.domain.dtos.offer.OfferRegisterDto;
import org.softuni.mobilele.domain.entities.Brand;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.services.BrandService;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @ModelAttribute("brands")
    public List<String> brands(){
        return this.brandService.getAllBrandsNames();
    }
    @GetMapping("/offers/add")
    public ModelAndView addOfferPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if(!model.containsAttribute("offerRegisterDto")) {
            modelAndView.addObject("offerRegisterDto", new OfferRegisterDto());
        }
        modelAndView.setViewName("offer-add");
        return modelAndView;
    }

    @PostMapping("/offers/add")
    public ModelAndView addOffer(@Valid OfferRegisterDto offerRegisterDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:/offers/add"); // Return to the same view
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerRegisterDto", bindingResult);
            redirectAttributes.addFlashAttribute("offerRegisterDto", offerRegisterDto);
            return modelAndView;
        }

        try {
            this.offerService.addOffer(offerRegisterDto);
            modelAndView.setViewName("redirect:/home"); // Redirect to the home page
        } catch (Exception e) {
            // Handle the exception if needed
            bindingResult.rejectValue("photo", "error.offerRegisterDto", "Failed to upload photo.");
            System.out.println(e.getMessage());
            modelAndView.setViewName("redirect:/offers/add"); // Return to the same view with the error message
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerRegisterDto", bindingResult);
            redirectAttributes.addFlashAttribute("offerRegisterDto", offerRegisterDto);
        }

        return modelAndView;
    }
}