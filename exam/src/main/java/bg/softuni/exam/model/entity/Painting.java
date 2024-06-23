package bg.softuni.exam.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "paintings")
@Getter
@Setter
@NoArgsConstructor
public class Painting extends BaseEntity{

    @Length(min = 5,max = 40)
    private String name;

    @Length(min = 5,max = 40)
    private String author;

    @ManyToOne
    private Style style;

    @ManyToOne
    private User owner;

    @Column(name = "is_favoourite")
    private Boolean isFavourite;

    @NotNull
    @Column
    private Integer votes;
}
