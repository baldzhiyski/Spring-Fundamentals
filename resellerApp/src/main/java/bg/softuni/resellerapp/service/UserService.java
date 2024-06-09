package bg.softuni.resellerapp.service;

import bg.softuni.resellerapp.model.dtos.user.UserLogInDto;
import bg.softuni.resellerapp.model.dtos.user.UserRegisterDto;
import bg.softuni.resellerapp.util.CurrentLoggedUser;

public interface UserService {

    boolean isLogged();

    CurrentLoggedUser getLoggedUser();

    boolean logIn(UserLogInDto userLogInDto);

    void register(UserRegisterDto userRegisterDto);

    void logOutCurrentUser();

    boolean userByUsernameExists(String username);

    boolean usernameByEmailExists(String email);
}
