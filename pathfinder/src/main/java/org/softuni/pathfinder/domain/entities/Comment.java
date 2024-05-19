package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity{

    @Column
    private boolean approved;

    @Column
    private Date created;

    @Column(name = "text_content" , columnDefinition = "TEXT")
    private String textContent;

    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

}
