package bg.softuni.dictionaryapp.service.impl;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.Word;
import bg.softuni.dictionaryapp.model.dtos.word.AddWordDto;
import bg.softuni.dictionaryapp.model.dtos.word.WordDto;
import bg.softuni.dictionaryapp.model.dtos.word.WordsWrapperDto;
import bg.softuni.dictionaryapp.model.enums.LanguageName;
import bg.softuni.dictionaryapp.repo.LanguageRepository;
import bg.softuni.dictionaryapp.repo.UserRepository;
import bg.softuni.dictionaryapp.repo.WordRepository;
import bg.softuni.dictionaryapp.service.WordsService;
import bg.softuni.dictionaryapp.util.CurrentLoggedInUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WordsServiceImpl implements WordsService {
    private WordRepository wordRepository;
    private LanguageRepository languageRepository;
    private CurrentLoggedInUser currentLoggedInUser;

    private UserRepository userRepository;

    public WordsServiceImpl(WordRepository wordRepository, LanguageRepository languageRepository, CurrentLoggedInUser currentLoggedInUser, UserRepository userRepository) {
        this.wordRepository = wordRepository;
        this.languageRepository = languageRepository;
        this.currentLoggedInUser = currentLoggedInUser;
        this.userRepository = userRepository;
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

    @Override
    @Transactional
    public void addWord(AddWordDto addWordDto) {
        Word word = new Word();
        word.setAddedBy(this.userRepository.getByUsername(currentLoggedInUser.getUsername()).orElseThrow())
                .setExample(addWordDto.getExample())
                .setTerm(addWordDto.getTerm())
                .setLanguage(this.languageRepository.findByName(addWordDto.getLanguageName()))
                .setTranslation(addWordDto.getTranslation())
                .setInputDate(addWordDto.getInputDate());

        this.wordRepository.saveAndFlush(word);
    }

    @Override
    public Optional<Word> getById(UUID uuid) {
        return this.wordRepository.findById(uuid);
    }

    @Override
    @Transactional
    public void remove(Word word) {
        this.wordRepository.deleteById(word.getId());
    }

    @Override
    public void removeAllWords() {
        this.wordRepository.deleteAll();
    }
}
