package org.softuni.pathfinder.domain.dtos.routes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteWithRandomPicDto {
    private Long id;
    private String name;
    private String randomImageUrl;
    private String description;
}
