package bg.softuni.dictionaryapp.model;

import bg.softuni.dictionaryapp.model.enums.LanguageName;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "languages")
public class Language extends BaseEntity{

    private LanguageName name;

    private String description;

    @OneToMany(mappedBy = "language")
    private Set<Word> words;

    public Language() {
    }

    public LanguageName getName() {
        return name;
    }

    public Language setName(LanguageName name) {
        this.name = name;
        String description;
        switch (name){
            case FRENCH -> description="A Romance language spoken worldwide, known for its elegance and cultural richness. It's the official language of France and numerous nations, famed for its cuisine, art, and literature.";
            case SPANISH -> description="A Romance language, is spoken by over 460 million people worldwide. It boasts a rich history, diverse dialects, and is known for its melodious sound, making it a global cultural treasure.";
            case GERMAN -> description="A West Germanic language, is spoken by over 90 million people worldwide. Known for its complex grammar and compound words, it's the official language of Germany and widely used in Europe.";
            case ITALIAN -> description="A Romance language spoken in Italy and parts of Switzerland, with rich cultural heritage. Known for its melodious sounds, it's a gateway to Italian art, cuisine, and history.";
            default -> throw new IllegalArgumentException("Incompatible language name !");
        }
        setDescription(description);
        return this;
    }

    public Set<Word> getWords() {
        return words;
    }

    public Language setWords(Set<Word> words) {
        this.words = words;
        return this;
    }
    public String getDescription() {
        return description;
    }

    public Language setDescription(String description) {
        this.description = description;
        return this;
    }
}
