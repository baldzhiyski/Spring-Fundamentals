package org.softuni.mobilele.domain.dtos.offer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.mobilele.domain.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferRegisterDto {

    @NotEmpty(message = "Brand is required")
    private String brand;
    @NotEmpty(message = "Category is required")
    private String category;

    @NotNull(message = "Name is required")
    private String name;
    @Positive(message = "Price can not be negative !")
    @NotNull(message = "Price is required")
    private Long price;
    @NotEmpty(message = "Type of engine is required")
    private String engine;
    @NotEmpty(message = "Transmission is required")
    private String transmission;

    @NotNull(message = "Year is required")
    private Year year;

    @Positive(message = "The mileage can not be negative !")
    @NotNull(message = "Please write a mileage")
    private Long mileage;

    @NotNull(message = "Please write description")
    private String description;

    @NotNull(message = "Please select a photo")
    private MultipartFile photo;

    private User seller;

}
