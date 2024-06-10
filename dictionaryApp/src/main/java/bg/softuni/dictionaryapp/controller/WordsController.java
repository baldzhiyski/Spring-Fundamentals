package bg.softuni.dictionaryapp.controller;

import bg.softuni.dictionaryapp.service.WordsService;
import bg.softuni.dictionaryapp.util.CurrentLoggedInUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class WordsController {
    private CurrentLoggedInUser currentLoggedInUser;

    private WordsService wordsService;

    public WordsController(CurrentLoggedInUser currentLoggedInUser, WordsService wordsService) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.wordsService = wordsService;
    }


}
