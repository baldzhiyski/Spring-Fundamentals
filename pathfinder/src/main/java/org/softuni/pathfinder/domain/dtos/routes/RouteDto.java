package org.softuni.pathfinder.domain.dtos.routes;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
public class RouteDto {

    @NotEmpty(message = "Please add a name")
    @Length(min = 2,max = 50)
    private String name;

    @NotEmpty(message = "Please add a short description")
    @Length(min = 5)
    private String description;

    private MultipartFile gpxCoordinates;

    private Set<String> categories;

    @NotEmpty(message = "Select a level")
    private String level;

    @NotEmpty(message = "Add a video url ")
    private String videoUrl;

    @NotNull(message = "Please select a photo")
    private MultipartFile image;
}
