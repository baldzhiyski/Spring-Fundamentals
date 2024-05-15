package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{
    @Column
    private String title;

    @Column
    private String url;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Route route;
}
