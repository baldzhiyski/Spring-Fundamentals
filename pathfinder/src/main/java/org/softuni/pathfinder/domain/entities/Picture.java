package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pictures")
@Getter
@Setter
public class Picture extends BaseEntity{
    @Column
    private String title;

    @Column
    private String url;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private User author;

    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private Route route;
}
