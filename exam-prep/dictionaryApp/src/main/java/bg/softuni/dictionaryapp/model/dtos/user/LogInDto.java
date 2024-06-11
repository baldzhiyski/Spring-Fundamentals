package bg.softuni.dictionaryapp.model.dtos.user;

import bg.softuni.dictionaryapp.validation.annotations.CorrectUser;

@CorrectUser
public class LogInDto {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public LogInDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LogInDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
