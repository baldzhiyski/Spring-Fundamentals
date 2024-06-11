package bg.softuni.resellerapp.model.dtos.user;

import jakarta.validation.constraints.NotBlank;

public class UserLogInDto {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserLogInDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserLogInDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
