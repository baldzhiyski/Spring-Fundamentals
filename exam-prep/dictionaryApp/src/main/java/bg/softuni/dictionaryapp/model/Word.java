package bg.softuni.dictionaryapp.model;

import bg.softuni.dictionaryapp.validation.annotations.DateNotInTheFuture;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Table(name = "words")
public class Word extends BaseEntity{

    @ManyToOne
    private User addedBy;

    @Length(min = 2,max = 40)
    private String term;

    @Length(min = 2,max = 80)
    private String translation;

    @Length(min = 2,max = 200)
    private String example;

    @Column(name = "input_date",nullable = false)
    @DateNotInTheFuture
    private Date inputDate;

    @ManyToOne
    private Language language;

    public Word() {
    }

    public User getAddedBy() {
        return addedBy;
    }

    public Word setAddedBy(User addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public String getTerm() {
        return term;
    }

    public Word setTerm(String term) {
        this.term = term;
        return this;
    }

    public String getTranslation() {
        return translation;
    }

    public Word setTranslation(String translation) {
        this.translation = translation;
        return this;
    }

    public String getExample() {
        return example;
    }

    public Word setExample(String example) {
        this.example = example;
        return this;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public Word setInputDate(Date inputDate) {
        this.inputDate = inputDate;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public Word setLanguage(Language language) {
        this.language = language;
        return this;
    }
}
