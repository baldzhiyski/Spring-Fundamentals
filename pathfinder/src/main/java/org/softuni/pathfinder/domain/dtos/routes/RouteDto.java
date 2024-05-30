package org.softuni.pathfinder.domain.dtos.routes;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.validation.anotations.FileAnnotation;
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

    @NotNull(message = "Please select a GPX file")
    @FileAnnotation(contentTypes = "text/plain")
    private MultipartFile gpxCoordinates;

    @NotEmpty(message = "At least one category must be selected")
    private Set<String> categories;

    @NotNull(message = "Select a level")
    private Level level;

    @NotEmpty(message = "Add a video url ")
    private String videoUrl;

    @NotNull(message = "Please select a photo")
    @FileAnnotation(contentTypes = "image/jpeg")
    private MultipartFile image;
}
