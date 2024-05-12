package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.dtos.user.UserDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);
}
