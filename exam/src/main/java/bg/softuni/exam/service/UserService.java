package bg.softuni.exam.service;

import bg.softuni.exam.model.dto.LogInDto;
import bg.softuni.exam.model.dto.RegisterDto;
import bg.softuni.exam.model.entity.User;
import bg.softuni.exam.util.CurrentLoggedUser;

public interface UserService {

    CurrentLoggedUser getLoggedUser();

    boolean userByUsernameExists(String username);

    User getUserByUsername(String username);

    boolean usernameByEmailExists(String email);

    void logIn(LogInDto logInDto);

    void registerUser(RegisterDto userRegisterDto);

    void logOutCurrentUser();
}
