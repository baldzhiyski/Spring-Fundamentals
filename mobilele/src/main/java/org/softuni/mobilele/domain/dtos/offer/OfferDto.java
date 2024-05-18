package org.softuni.mobilele.domain.dtos.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.mobilele.domain.dtos.model.ModelIdDto;
import org.softuni.mobilele.domain.dtos.user.UserIdDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private String description;

    private String engine;

    private Long mileage;

    private Double price;

    private String transmission;

    private String year;

    private String created;

    private String modified;

    private ModelIdDto model;

    private UserIdDto seller;
}
