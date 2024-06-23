package bg.softuni.exam.controller;

import bg.softuni.exam.model.dto.painting.AddPaintingDto;
import bg.softuni.exam.service.PaintingService;
import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class PaintingsController {
    private UserService userService;
    private PaintingService paintingService;

    public PaintingsController(UserService userService, PaintingService paintingService) {
        this.userService = userService;
        this.paintingService = paintingService;
    }
    @ModelAttribute("currentUser")
    public CurrentLoggedUser currentLoggedUser() {
        return this.userService.getLoggedUser();
    }

    @PostMapping("/paintings/remove/{id}")
    public String deleteFromCreated(@PathVariable UUID id){
        userService.removePainting(id);
        return "redirect:/home";
    }

    @PostMapping("/paintings/addToFavorites/{id}")
    public String addToFav(@PathVariable UUID id){
        userService.addToFavourites(id);
        return "redirect:/home";
    }

    @PostMapping("/paintings/vote/{id}")
    public String voteToPicture(@PathVariable UUID id){
        userService.addVote(id);
        return "redirect:/home";
    }

    @GetMapping("/paintings/add/{id}")
    public String addPage(@PathVariable UUID id, Model model){
        if(!this.userService.getLoggedUser().isLogged()){
            return "redirect:/";
        }
        if(!model.containsAttribute("addPaintingDto")){
            model.addAttribute("addPaintingDto" , new AddPaintingDto());
        }
        return "add-painting";
    }

    @PostMapping("/paintings/add/{id}")
    public ModelAndView add(@PathVariable UUID id , @Valid AddPaintingDto addPaintingDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPaintingDto", bindingResult);
            redirectAttributes.addFlashAttribute("addPaintingDto", addPaintingDto);
            modelAndView.setViewName("redirect:/paintings/add/" + id);
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/home");
        paintingService.addPainting(addPaintingDto);
        return modelAndView;
    }


}
