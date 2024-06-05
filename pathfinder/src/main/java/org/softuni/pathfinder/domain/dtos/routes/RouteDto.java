package org.softuni.pathfinder.domain.dtos.routes;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.validation.anotations.AtLeastOneCategory;
import org.softuni.pathfinder.validation.anotations.FileAnnotation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @FileAnnotation(contentTypes = "text/plain",message = "File is required . It should be text/plain .")
    private MultipartFile gpxCoordinates;

    @AtLeastOneCategory
    private List<CategoryName> categories;

    @NotNull(message = "Select a level")
    private Level level;

    @NotEmpty(message = "Add a video url ")
    private String videoUrl;

    @NotNull(message = "Please select at least one photo")
    private List< @FileAnnotation(contentTypes = "images/jpeg") MultipartFile> images;
}
