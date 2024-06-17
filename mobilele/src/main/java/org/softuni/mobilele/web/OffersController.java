package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.domain.dtos.offer.*;
import org.softuni.mobilele.domain.entities.Brand;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.services.BrandService;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.softuni.pathfinder.domain.dtos.comments.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/offers/all")
    public ModelAndView getOffers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("offers");

        List<OfferDisplayDto> offers = offerService.getAllOffers(); // Fetch offers from the service layer
        modelAndView.addObject("offersDtos", offers); // Pass offers data to the view


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

    @GetMapping("/offers/details/{id}")
    public ModelAndView getDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        OfferDetailsDto offerDtoById = this.offerService.getOfferById(id);

        modelAndView.addObject("loggedIsCreator",this.userService.isLoggedCreator(id));
        modelAndView.addObject("offerDtoById",offerDtoById);
        modelAndView.setViewName("details");
        return modelAndView;
    }

    @PostMapping("/offers/details/{id}")
    public ModelAndView deleteIfCreator(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();

        if(!this.userService.isLoggedCreator(id)){
            modelAndView.setViewName("details");
            modelAndView.addObject("errorMessage", "You are not authorized to delete this offer.");
            return modelAndView;
        }

        this.offerService.deleteOffer(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/offers/update/{id}")
    public ModelAndView getUpdateViewIfCreator(@PathVariable Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("offerDtoById",this.offerService.getOfferById(id)); // Pass offer details to the view
        if(!model.containsAttribute("offerUpdateDto")) {
            modelAndView.addObject("offerUpdateDto", new OfferUpdateDto()); // Initialize OfferUpdateDto
        }
        modelAndView.setViewName("update");
        return modelAndView;
    }

    @PostMapping("/offers/update/{id}")
    public ModelAndView updateTheOfferIfCreator(@PathVariable Long id,@Valid OfferUpdateDto offerUpdateDto,
                                                BindingResult bindingResult,RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("redirect:/offers/update/{id}"); // Return to the same view with the error message
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerUpdateDto", bindingResult);
            redirectAttributes.addFlashAttribute("offerUpdateDto", offerUpdateDto);
            return modelAndView;
        }

        this.offerService.updateOffer(id,offerUpdateDto);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}