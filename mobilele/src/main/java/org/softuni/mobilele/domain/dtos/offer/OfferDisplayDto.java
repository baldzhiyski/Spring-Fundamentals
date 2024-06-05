package org.softuni.mobilele.domain.dtos.offer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.mobilele.domain.entities.enums.Engine;
import org.softuni.mobilele.domain.entities.enums.Transmission;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
public class OfferDisplayDto {
    private Long id;
    private String modelImageUrl;
    private Year year;
    private String modelBrandName;
    private String modelName;
    private Long mileage;
    private Long price;
    private Engine engine;

    private Transmission transmission;
}
