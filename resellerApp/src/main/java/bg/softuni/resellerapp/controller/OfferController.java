package bg.softuni.resellerapp.controller;

import bg.softuni.resellerapp.model.dtos.offer.AddOfferDto;
import bg.softuni.resellerapp.service.OfferService;
import bg.softuni.resellerapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OfferController {
    private UserService userService;
    private OfferService offerService;

    public OfferController(UserService userService, OfferService offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @ModelAttribute("isLogged")
    public boolean isCurrentUserLogged() {
        return userService.isLogged();
    }
    @GetMapping("/add/offer")
    public String addOfferPage(Model model){
        if(!this.userService.isLogged()){
            return "redirect:/";
        }
        if(!model.containsAttribute("offerDto")){
            model.addAttribute("offerDto", new AddOfferDto());
        }
        return "offer-add";
    }

    @PostMapping("/add/offer")
    public ModelAndView addOffer(@Valid AddOfferDto offerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerDto", bindingResult);
            redirectAttributes.addFlashAttribute("offerDto", offerDto);
            modelAndView.setViewName("redirect:/add/offer");
            return modelAndView;
        }

        this.offerService.registerOffer(offerDto);
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
