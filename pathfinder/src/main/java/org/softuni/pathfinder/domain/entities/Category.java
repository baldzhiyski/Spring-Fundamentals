package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category  extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private CategoryName name;

    @Column(columnDefinition = "TEXT")
    private String description;

}
