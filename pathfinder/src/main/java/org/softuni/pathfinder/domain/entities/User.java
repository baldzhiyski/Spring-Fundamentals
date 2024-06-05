package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;
import org.softuni.pathfinder.domain.entities.enums.Level;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{

    @Column(nullable = false)
    @Length(min = 2)
    private String username;

    @Length(min = 2)
    @Column(nullable = false)
    private String password;

    @Column
    private Integer age;

    @Column(name = "full_name")
    @Length(min = 2)
    private String fullName;

    @OneToMany(mappedBy = "author")
    private List<Picture> pictures;

    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"))
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    private Level level;

    @PrePersist
    public void hashPassword() {
        this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

}
