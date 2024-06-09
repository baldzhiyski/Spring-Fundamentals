package bg.softuni.resellerapp.service;

import bg.softuni.resellerapp.model.dtos.UserLogInDto;
import bg.softuni.resellerapp.model.dtos.UserRegisterDto;
import bg.softuni.resellerapp.util.CurrentLoggedUser;

public interface UserService {

    boolean isLogged();

    CurrentLoggedUser getLoggedUser();

    boolean logIn(UserLogInDto userLogInDto);

    void register(UserRegisterDto userRegisterDto);

    void logOut();

    boolean userByUsernameExists(String username);

    boolean usernameByEmailExists(String email);
}
