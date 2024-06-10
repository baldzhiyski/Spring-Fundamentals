package bg.softuni.dictionaryapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(unique = true,nullable = false)
    @Length(min = 2,max = 40)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @NotNull
    private String email;

    @OneToMany(mappedBy = "addedBy")
    private Set<Word> addWords;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Word> getAddWords() {
        return addWords;
    }

    public User setAddWords(Set<Word> addWords) {
        this.addWords = addWords;
        return this;
    }
}
