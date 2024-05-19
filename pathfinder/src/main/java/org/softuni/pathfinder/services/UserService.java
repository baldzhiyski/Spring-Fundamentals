package org.softuni.pathfinder.services;

import org.softuni.pathfinder.domain.dtos.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.utils.LoggedInUser;

public interface UserService {
    boolean userByUsernameExists(String username);

     boolean usernameByEmailExists(String email);

    void register(UserRegisterDto userRegisterDto);

    void login(UserLogInDto userLogInDto);

    boolean checkPasswordCorrectForTheUsername(UserLogInDto userLogInDto);

    boolean logOut();

    boolean isLoggedIn();

    boolean isAdmin();

    LoggedInUser getLoggedInUser();
}
