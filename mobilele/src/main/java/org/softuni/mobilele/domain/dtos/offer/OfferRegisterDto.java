package org.softuni.mobilele.domain.dtos.offer;

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

    private String brand;
    private String model;
    private String name;
    private Long price;
    private String engine;
    private String transmission;
    private Year year;
    private Long mileage;

    private String description;

    private MultipartFile photo;

    private User seller;

}
