package org.softuni.mobilele.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.softuni.mobilele.domain.entities.enums.Category;

import java.util.Date;

@Entity
@Table(name = "models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model  extends BaseEntity{
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @Length(min = 8,max = 512)
    private String imageUrl;
    @Column
    private Long startYear;

    @Column
    private Long endYear;

    @Column
    private Date created;

    @Column
    private Date modified;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
