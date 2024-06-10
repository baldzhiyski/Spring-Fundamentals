package bg.softuni.dictionaryapp.model.dtos;

import bg.softuni.dictionaryapp.model.Word;

import java.util.Date;
import java.util.UUID;

public class WordDto {
    private UUID id;
    private String term;

    private String translation;

    private String example;

    private Date inputDate;

    private String addedByUsername;

    public WordDto(Word word){
        setExample(word.getExample());
        setTerm(word.getTerm());
        setInputDate(word.getInputDate());
        setAddedByUsername(word.getAddedBy().getUsername());
        setTranslation(word.getTranslation());
        setId(word.getId());
    }
    public UUID getId() {
        return id;
    }

    public WordDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTerm() {
        return term;
    }

    public String getTranslation() {
        return translation;
    }

    public WordDto setTranslation(String translation) {
        this.translation = translation;
        return this;
    }
    public WordDto setTerm(String term) {
        this.term = term;
        return this;
    }

    public String getExample() {
        return example;
    }

    public WordDto setExample(String example) {
        this.example = example;
        return this;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public WordDto setInputDate(Date inputDate) {
        this.inputDate = inputDate;
        return this;
    }

    public String getAddedByUsername() {
        return addedByUsername;
    }

    public WordDto setAddedByUsername(String addedByUsername) {
        this.addedByUsername = addedByUsername;
        return this;
    }
}
