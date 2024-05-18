package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.dtos.user.UserDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;

import java.io.IOException;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto) throws IOException;

    boolean userByUsernameExists(String username);


}
