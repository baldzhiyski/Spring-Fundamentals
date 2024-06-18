package org.softuni.pathfinder.services;

import org.softuni.pathfinder.domain.dtos.user.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.user.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.User;


public interface UserService {
    boolean userByUsernameExists(String username);

     boolean usernameByEmailExists(String email);

    void register(UserRegisterDto userRegisterDto);

    UserLogInDto getLoggedInUser();

    User getById(Long id);

    User getByUsername(String username);
}
