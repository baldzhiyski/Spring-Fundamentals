package bg.softuni.exam.service;

import bg.softuni.exam.model.dto.painting.WrapperPaintings;
import bg.softuni.exam.model.dto.user.LogInDto;
import bg.softuni.exam.model.dto.user.RegisterDto;
import bg.softuni.exam.model.entity.User;
import bg.softuni.exam.util.CurrentLoggedUser;

import java.util.UUID;

public interface UserService {

    CurrentLoggedUser getLoggedUser();

    boolean userByUsernameExists(String username);

    User getUserByUsername(String username);

    boolean usernameByEmailExists(String email);

    void logIn(LogInDto logInDto);

    void registerUser(RegisterDto userRegisterDto);

    void logOutCurrentUser();

    User getUserFromLogged();
    void removePainting(UUID paintingId);

    void addToFavourites(UUID paintingId);

    void addVote(UUID paintingId);
}
