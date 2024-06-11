package bg.softuni.dictionaryapp.service.impl;

import bg.softuni.dictionaryapp.model.User;
import bg.softuni.dictionaryapp.model.dtos.user.LogInDto;
import bg.softuni.dictionaryapp.model.dtos.user.UserRegisterDto;
import bg.softuni.dictionaryapp.repo.UserRepository;
import bg.softuni.dictionaryapp.service.UserService;
import bg.softuni.dictionaryapp.util.CurrentLoggedInUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private CurrentLoggedInUser currentLoggedInUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentLoggedInUser currentLoggedInUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentLoggedInUser = currentLoggedInUser;
    }

    @Override
    public boolean usernameByEmailExists(String email) {
        return userRepository.getByEmail(email).isPresent();
    }

    @Override
    public boolean userByUsernameExists(String username) {
        return this.userRepository.getByUsername(username).isPresent();
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = new User();

        user.setUsername(userRegisterDto.getUsername())
                .setEmail(userRegisterDto.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void logIn(LogInDto logInDto) {
        currentLoggedInUser.setLogged(true);
        currentLoggedInUser.setId(this.userRepository.getByUsername(logInDto.getUsername()).orElseThrow().getId());
        currentLoggedInUser.setUsername(logInDto.getUsername());
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getByUsername(username).orElse(null);
    }

    @Override
    public void logOutCurrentUser() {
        this.currentLoggedInUser.setLogged(false);
        this.currentLoggedInUser.setUsername("");
    }
}
