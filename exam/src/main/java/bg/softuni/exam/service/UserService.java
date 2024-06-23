package bg.softuni.exam.service;

import bg.softuni.exam.util.CurrentLoggedUser;

public interface UserService {

    CurrentLoggedUser getLoggedUser();
}
