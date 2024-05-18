package org.softuni.mobilele.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.softuni.mobilele.domain.entities.enums.Engine;
import org.softuni.mobilele.domain.entities.enums.Transmission;

import java.time.Year;
import java.util.Date;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseEntity{

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private Engine engine;

    @Column
    private Long mileage;

    @Column
    private Double price;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Column
    private Year year;

    @Column
    private Date created;

    @Column
    private Date modified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private User seller;
}
