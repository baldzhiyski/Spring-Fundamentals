package org.softuni.mobilele.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Date created;

    @Column
    private Date modified;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Model> models;
}
