package bg.softuni.dictionaryapp.model.dtos.word;

import java.util.HashSet;
import java.util.Set;

public class WordsWrapperDto {

    private Set<WordDto> germanWords;

    private Set<WordDto> frenchWords;

    private Set<WordDto> spanishWords;

    private Set<WordDto> italianWords;

    public WordsWrapperDto() {
        this.frenchWords= new HashSet<>();
        this.germanWords = new HashSet<>();
        this.spanishWords = new HashSet<>();
        this.italianWords = new HashSet<>();
    }

    public Integer size(){
        return this.italianWords.size() + this.getGermanWords().size()
                + this.spanishWords.size() + this.frenchWords.size();
    }
    public Set<WordDto> getGermanWords() {
        return germanWords;
    }

    public WordsWrapperDto setGermanWords(Set<WordDto> germanWords) {
        this.germanWords = germanWords;
        return this;
    }

    public Set<WordDto> getFrenchWords() {
        return frenchWords;
    }

    public WordsWrapperDto setFrenchWords(Set<WordDto> frenchWords) {
        this.frenchWords = frenchWords;
        return this;
    }

    public Set<WordDto> getSpanishWords() {
        return spanishWords;
    }

    public WordsWrapperDto setSpanishWords(Set<WordDto> spanishWords) {
        this.spanishWords = spanishWords;
        return this;
    }

    public Set<WordDto> getItalianWords() {
        return italianWords;
    }

    public WordsWrapperDto setItalianWords(Set<WordDto> italianWords) {
        this.italianWords = italianWords;
        return this;
    }
}
