package bg.softuni.dictionaryapp.controller;

import bg.softuni.dictionaryapp.model.Word;
import bg.softuni.dictionaryapp.model.dtos.word.AddWordDto;
import bg.softuni.dictionaryapp.service.WordsService;
import bg.softuni.dictionaryapp.util.CurrentLoggedInUser;
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
public class WordsController {
    private CurrentLoggedInUser currentLoggedInUser;

    private WordsService wordsService;

    public WordsController(CurrentLoggedInUser currentLoggedInUser, WordsService wordsService) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.wordsService = wordsService;
    }
    @ModelAttribute("loggedInUser")
    public CurrentLoggedInUser getLoggedUser(){
        return currentLoggedInUser;
    }

    @GetMapping("/add/word")
    public String getAddPage(Model model){
        if(!this.currentLoggedInUser.isLogged()){
            return "redirect:/";
        }
        if(!model.containsAttribute("addWordDto")){
            model.addAttribute("addWordDto", new AddWordDto());
        }

        return "word-add";
    }
    @PostMapping("/add/word")
    public ModelAndView addWord(@Valid AddWordDto addWordDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addWordDto", bindingResult);
            redirectAttributes.addFlashAttribute("addWordDto", addWordDto);
            modelAndView.setViewName("redirect:/add/word");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/home");
        this.wordsService.addWord(addWordDto);
        return modelAndView;
    }
    @PostMapping("/word/remove/{id}")
    public ModelAndView removeWord(@PathVariable UUID id){
        Word word = this.wordsService.getById(id).orElseThrow();
        this.wordsService.remove(word);
        return  new ModelAndView("redirect:/home");
    }

    @PostMapping("/remove/all-words")
    public ModelAndView removeAllWords(){
        this.wordsService.removeAllWords();
        return new ModelAndView("redirect:/home");
    }
}
