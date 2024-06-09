package bg.softuni.resellerapp.model.dtos.user;

import bg.softuni.resellerapp.vallidation.annotations.PasswordMatch;
import bg.softuni.resellerapp.vallidation.annotations.UniqueEmail;
import bg.softuni.resellerapp.vallidation.annotations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

@PasswordMatch
public class UserRegisterDto {

    @Length(min = 2, max = 50)
    @UniqueUsername
    private String username;

    @Email
    @UniqueEmail
    private String email;
    @Min(value = 4,message = "The password should be at least 4 symbols.")
    private String password;
    @Min(value = 4,message = "The password should be at least 4 symbols.")
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
