package bg.softuni.dictionaryapp.service;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.dtos.word.AddWordDto;
import bg.softuni.dictionaryapp.model.dtos.word.WordDto;
import bg.softuni.dictionaryapp.model.dtos.word.WordsWrapperDto;

import java.util.Set;

public interface WordsService {

    Set<WordDto> getWordsByLanguage(Language language);

    WordsWrapperDto getWordsWrapperDto();

    void addWord(AddWordDto addWordDto);
}
