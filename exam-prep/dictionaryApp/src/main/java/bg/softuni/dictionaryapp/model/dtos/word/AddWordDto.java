package bg.softuni.dictionaryapp.model.dtos.word;

import bg.softuni.dictionaryapp.model.enums.LanguageName;
import bg.softuni.dictionaryapp.validation.annotations.DateNotInTheFuture;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class AddWordDto {

    @Length(min = 2,max = 40)
    private String term;

    @Length(min = 2,max = 80)
    private String translation;

    @Length(min = 2,max = 200)
    private String example;

    @DateNotInTheFuture
    private Date inputDate;

    @NotNull(message = "Language is required !")
    private LanguageName languageName;

    public String getTerm() {
        return term;
    }

    public AddWordDto setTerm(String term) {
        this.term = term;
        return this;
    }

    public String getTranslation() {
        return translation;
    }

    public AddWordDto setTranslation(String translation) {
        this.translation = translation;
        return this;
    }

    public String getExample() {
        return example;
    }

    public AddWordDto setExample(String example) {
        this.example = example;
        return this;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public AddWordDto setInputDate(Date inputDate) {
        this.inputDate = inputDate;
        return this;
    }

    public LanguageName getLanguageName() {
        return languageName;
    }

    public AddWordDto setLanguageName(LanguageName languageName) {
        this.languageName = languageName;
        return this;
    }
}
