package org.softuni.pathfinder.services;

import org.softuni.pathfinder.domain.dtos.UserRegisterDto;

public interface UserService {
    boolean userByUsernameExists(String username);

     boolean usernameByEmailExists(String email);

    void register(UserRegisterDto userRegisterDto);
}
