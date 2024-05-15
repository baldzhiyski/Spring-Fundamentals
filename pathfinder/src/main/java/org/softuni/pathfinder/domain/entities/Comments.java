package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comments  extends BaseEntity{

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
