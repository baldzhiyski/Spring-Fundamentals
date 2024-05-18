package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.dtos.user.UserDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.domain.entities.User;

import java.io.IOException;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto) throws IOException;

    boolean userByUsernameExists(String username);

    boolean checkValidUsernameAndPass(String username,String password);

    User getUserByUsername(String username);

}
