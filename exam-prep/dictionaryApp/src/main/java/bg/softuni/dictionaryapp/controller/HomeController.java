package bg.softuni.dictionaryapp.controller;

import bg.softuni.dictionaryapp.service.WordsService;
import bg.softuni.dictionaryapp.util.CurrentLoggedInUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private CurrentLoggedInUser currentLoggedInUser;

    private WordsService wordsService;

    public HomeController(CurrentLoggedInUser currentLoggedInUser, WordsService wordsService) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.wordsService = wordsService;
    }

    @ModelAttribute("loggedInUser")
    public CurrentLoggedInUser getLoggedUser(){
        return currentLoggedInUser;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        if(this.currentLoggedInUser.isLogged()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model){
        if(!this.currentLoggedInUser.isLogged()){
            return "redirect:/";
        }
        model.addAttribute("wrapperWordsDto",this.wordsService.getWordsWrapperDto());
        return "home";
    }
}
