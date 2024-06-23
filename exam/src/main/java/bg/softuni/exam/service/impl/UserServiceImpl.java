package bg.softuni.exam.service.impl;

import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {
    private CurrentLoggedUser currentLoggedUser;

    public UserServiceImpl(CurrentLoggedUser currentLoggedUser) {
        this.currentLoggedUser = currentLoggedUser;
    }

    @Override
    public CurrentLoggedUser getLoggedUser() {
        return currentLoggedUser;
    }
}
