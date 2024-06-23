package bg.softuni.exam.model.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String username;

    private String password;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private Set<Painting> createdPaintings;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Painting> favouritePaintings;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Painting> ratedPaintings;
}
