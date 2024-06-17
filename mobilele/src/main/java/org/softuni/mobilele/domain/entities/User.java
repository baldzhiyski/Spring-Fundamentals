package org.softuni.mobilele.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;
import org.softuni.mobilele.domain.entities.enums.Role;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User  extends BaseEntity{

    @Column
    private String username;

    @Column
    private String password;
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private boolean isActive;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    private UserRole role;

    @Column
    private String imageUrl;

    @Column
    private Date created;

    @Column
    private Date modified;

    @OneToMany(mappedBy = "seller")
    private List<Offer> offers;

}
