package org.softuni.mobilele.domain.dtos.offer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.mobilele.domain.entities.User;
import org.softuni.mobilele.domain.entities.enums.Category;
import org.softuni.mobilele.domain.entities.enums.Engine;
import org.softuni.mobilele.domain.entities.enums.Transmission;
import org.softuni.mobilele.validation.annotations.ValidFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferRegisterDto {

    @NotEmpty(message = "Brand is required")
    private String brand;
    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Name is required")
    private String name;
    @Positive(message = "Price can not be negative !")
    @NotNull(message = "Price is required")
    private Long price;
    @NotNull(message = "Type of engine is required")
    private Engine engine;
    @NotEmpty(message = "Transmission is required")
    private Transmission transmission;

    @NotNull(message = "Year is required")
    private Year year;

    @Positive(message = "The mileage can not be negative !")
    @NotNull(message = "Please write a mileage")
    private Long mileage;

    @NotNull(message = "Please write description")
    private String description;

    @ValidFile(allowedTypes = "text/plain")
    private MultipartFile photo;

    private User seller;

}
