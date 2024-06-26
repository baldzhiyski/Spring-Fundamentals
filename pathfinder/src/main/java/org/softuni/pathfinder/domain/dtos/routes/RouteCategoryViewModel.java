package org.softuni.pathfinder.domain.dtos.routes;

import lombok.Getter;
import lombok.Setter;
import org.softuni.pathfinder.domain.entities.Picture;
@Getter
@Setter
public class RouteCategoryViewModel {

    private long id;
    private String name;
    private String description;
    private String randomPicUrl;
}
