package bg.softuni.dictionaryapp.service;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.Word;
import bg.softuni.dictionaryapp.model.dtos.word.AddWordDto;
import bg.softuni.dictionaryapp.model.dtos.word.WordDto;
import bg.softuni.dictionaryapp.model.dtos.word.WordsWrapperDto;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface WordsService {

    Set<WordDto> getWordsByLanguage(Language language);

    WordsWrapperDto getWordsWrapperDto();

    void addWord(AddWordDto addWordDto);

    Optional<Word> getById(UUID uuid);

    void remove(Word word);

    void removeAllWords();
}
