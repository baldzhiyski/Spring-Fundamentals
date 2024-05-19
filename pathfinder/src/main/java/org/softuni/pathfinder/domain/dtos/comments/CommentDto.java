package org.softuni.pathfinder.domain.dtos.comments;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.User;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @NotEmpty(message = "Message is required !")
    private String textContent;

    private User author;

    private Date created;

    private Route route;
}
