package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.dtos.user.UserLogInDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.domain.entities.User;

import java.io.IOException;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto) throws IOException;

    boolean userByUsernameExists(String username);

    User getUserByUsername(String username);

    boolean isLoggedCreator(Long id);
}
