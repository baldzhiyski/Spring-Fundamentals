package bg.softuni.dictionaryapp.service;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.dtos.WordDto;
import bg.softuni.dictionaryapp.model.dtos.WordsWrapperDto;

import java.util.Set;

public interface WordsService {

    Set<WordDto> getWordsByLanguage(Language language);

    WordsWrapperDto getWordsWrapperDto();
}
