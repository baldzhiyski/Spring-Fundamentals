package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.pathfinder.domain.entities.enums.Level;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
public class Route  extends BaseEntity{

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "gpx_coordinates",columnDefinition = "LONGTEXT")
    @Lob
    private String gpxCoordinates;

    @Enumerated(EnumType.STRING)
    private Level level;


    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "routes_categories")
    private Set<Category> category;

    @OneToMany(mappedBy = "route",fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToOne(mappedBy = "route")
    private Picture picture;

}
