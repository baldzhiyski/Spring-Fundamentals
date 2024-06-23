package bg.softuni.exam.model.entity;

import bg.softuni.exam.model.entity.enums.StyleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "style")
@Getter
@Setter
@NoArgsConstructor
public class Style extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(unique = true,nullable = false)
    private StyleName styleName;

    @Column(nullable = false)
    private String description;

    public Style setStyleName(StyleName styleName) {
        String description;
        switch (styleName){
            case REALISM -> description="Also known as naturalism, this style of art is considered as 'real art' and has been the dominant style of painting since the Renaissance.";
            case EXPRESSIONISM -> description="Expressionism is a style of art that doesn't concern itself with realism.";
            case ABSTRACT -> description="Abstract art does not attempt to represent recognizable subjects in a realistic manner. ";
            case SURREALISM -> description= "Surrealism is characterized by dreamlike, fantastical imagery that often defies logical explanation.";
            case IMPRESSIONISM -> description="Impressionism is a painting style most commonly associated with the 19th century where small brush strokes are used to build up a larger picture.";
            default -> throw new IllegalStateException("Invalid style name !");
        }
        this.styleName = styleName;
        this.description=description;
        return this;
    }
}
