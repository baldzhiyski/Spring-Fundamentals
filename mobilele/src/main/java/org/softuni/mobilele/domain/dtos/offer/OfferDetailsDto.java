package org.softuni.mobilele.domain.dtos.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDetailsDto {
    private String description;

    private String engine;

    private Long mileage;

    private Double price;

    private String transmission;

    private String year;

    private String created;

    private String modified;

    private String modelName;
    private String modelImageUrl;
    private Long id;
    private String modelBrandName;
    private String sellerFirstName;
    private String sellerLastName;
}
