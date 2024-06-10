package bg.softuni.dictionaryapp.service.impl;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.dtos.WordDto;
import bg.softuni.dictionaryapp.model.dtos.WordsWrapperDto;
import bg.softuni.dictionaryapp.model.enums.LanguageName;
import bg.softuni.dictionaryapp.repo.LanguageRepository;
import bg.softuni.dictionaryapp.repo.WordRepository;
import bg.softuni.dictionaryapp.service.WordsService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordsServiceImpl implements WordsService {
    private WordRepository wordRepository;
    private LanguageRepository languageRepository;

    public WordsServiceImpl(WordRepository wordRepository, LanguageRepository languageRepository) {
        this.wordRepository = wordRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public Set<WordDto> getWordsByLanguage(Language language) {
        return wordRepository.findByLanguage(language).stream()
                .map(WordDto::new)
                .collect(Collectors.toSet());
    }

    @Override
    public WordsWrapperDto getWordsWrapperDto() {
        Set<WordDto> frenchWords = this.getWordsByLanguage(this.languageRepository.findByName(LanguageName.FRENCH));
        Set<WordDto> germanWords = this.getWordsByLanguage(this.languageRepository.findByName(LanguageName.GERMAN));
        Set<WordDto> italianWords = this.getWordsByLanguage(this.languageRepository.findByName(LanguageName.ITALIAN));
        Set<WordDto> spanishWords = this.getWordsByLanguage(this.languageRepository.findByName(LanguageName.SPANISH));

        WordsWrapperDto wordsWrapperDto = new WordsWrapperDto();
         return wordsWrapperDto.setFrenchWords(frenchWords)
                .setGermanWords(germanWords)
                .setItalianWords(italianWords)
                .setSpanishWords(spanishWords);
    }
}
