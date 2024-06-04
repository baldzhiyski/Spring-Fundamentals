package org.softuni.mobilele.domain.dtos.offer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
import org.softuni.mobilele.validation.annotations.YearNotInTheFuture;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferRegisterDto {

    // TODO : Fix the logic here and in the controller

    @NotEmpty(message = "Brand is required")
    private String brand;
    @NotNull(message = "Category is required")
    private Category category;

    @NotEmpty(message = "Name is required")
    private String name;
    @Positive(message = "Price can not be negative !")
    @NotNull(message = "Price is required")
    private BigInteger price;
    @NotNull(message = "Type of engine is required")
    private Engine engine;
    @NotNull(message = "Transmission is required")
    private Transmission transmission;

    @NotNull(message = "Year is required")
    @YearNotInTheFuture
    @Min(1930)
    private Year year;

    @Positive(message = "The mileage can not be negative !")
    @NotNull(message = "Please write a mileage")
    private Long mileage;

    @NotEmpty(message = "Add some description")
    private String description;

    @ValidFile(allowedTypes = "image/jpeg")
    private MultipartFile photo;

}
