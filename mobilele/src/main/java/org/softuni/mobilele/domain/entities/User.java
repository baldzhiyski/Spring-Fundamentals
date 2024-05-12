package org.softuni.mobilele.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.softuni.mobilele.domain.entities.enums.Role;

import java.util.Date;

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

    @OneToOne
    @JoinColumn(name = "user_role_id")
    private UserRole role;

    @Column
    private String imageUrl;

    @Column
    private Date created;

    @Column
    private Date modified;
}
