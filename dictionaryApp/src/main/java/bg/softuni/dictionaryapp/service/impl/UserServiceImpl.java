package bg.softuni.dictionaryapp.service.impl;

import bg.softuni.dictionaryapp.model.User;
import bg.softuni.dictionaryapp.model.dtos.LogInDto;
import bg.softuni.dictionaryapp.model.dtos.UserRegisterDto;
import bg.softuni.dictionaryapp.repo.UserRepository;
import bg.softuni.dictionaryapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    }
}
