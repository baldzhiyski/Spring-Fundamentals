package org.softuni.mobilele.domain.dtos.offer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.mobilele.domain.entities.enums.Category;
import org.softuni.mobilele.domain.entities.enums.Engine;
import org.softuni.mobilele.domain.entities.enums.Transmission;
import org.softuni.mobilele.validation.annotations.ValidFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.Year;

@Setter
@Getter
@NoArgsConstructor
public class OfferUpdateDto {

    private String brand;
    private Category category;

    @NotEmpty(message = "Name is required")
    private String name;
    @Positive(message = "Price can not be negative !")
    private BigInteger price;
    private Engine engine;
    @NotNull(message = "Transmission is required")
    private Transmission transmission;

    private Year year;

    @Positive(message = "The mileage can not be negative !")
    private Long mileage;

    private String description;

    @ValidFile(allowedTypes = "image/jpeg")
    private MultipartFile photo;
}
