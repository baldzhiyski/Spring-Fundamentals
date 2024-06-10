package bg.softuni.dictionaryapp.service;

import bg.softuni.dictionaryapp.model.User;
import bg.softuni.dictionaryapp.model.dtos.LogInDto;
import bg.softuni.dictionaryapp.model.dtos.UserRegisterDto;

public interface UserService {
    boolean usernameByEmailExists(String email);

    boolean userByUsernameExists(String username);

    void registerUser(UserRegisterDto userRegisterDto);

    void logIn(LogInDto logInDto);

    User getUserByUsername(String username);

    void logOutCurrentUser();
}
