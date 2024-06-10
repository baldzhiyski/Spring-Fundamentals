package bg.softuni.dictionaryapp.model.dtos;

import bg.softuni.dictionaryapp.validation.annotations.PasswordMatch;
import bg.softuni.dictionaryapp.validation.annotations.UniqueEmail;
import bg.softuni.dictionaryapp.validation.annotations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@PasswordMatch
public class UserRegisterDto {

    @UniqueUsername
    @Length(min = 2,max = 40)
    private String username;

    @Email
    @UniqueEmail
    private String email;

    @NotEmpty(message = "Password field is required")
    private String password;

    @NotEmpty(message = "Confirm Pass field is required")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
