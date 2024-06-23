package bg.softuni.exam.service.impl;

import bg.softuni.exam.model.dto.LogInDto;
import bg.softuni.exam.model.dto.RegisterDto;
import bg.softuni.exam.model.entity.User;
import bg.softuni.exam.repository.UserRepository;
import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {
    private CurrentLoggedUser currentLoggedUser;
    private UserRepository userRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(CurrentLoggedUser currentLoggedUser, UserRepository userRepository, ModelMapper mapper,  PasswordEncoder passwordEncoder) {
        this.currentLoggedUser = currentLoggedUser;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CurrentLoggedUser getLoggedUser() {
        return currentLoggedUser;
    }

    @Override
    public boolean userByUsernameExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public boolean usernameByEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void logIn(LogInDto logInDto) {
        User mapped = this.mapper.map(logInDto, User.class);
        this.currentLoggedUser.setLogged(true);
        this.currentLoggedUser.setId(mapped.getId());
        this.currentLoggedUser.setUsername(mapped.getUsername());
    }

    @Override
    public void registerUser(RegisterDto userRegisterDto) {
        User userToBeAdded = this.mapper.map(userRegisterDto, User.class);
        userToBeAdded.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        this.userRepository.saveAndFlush(userToBeAdded);
    }

    @Override
    public void logOutCurrentUser() {
        this.currentLoggedUser = new CurrentLoggedUser();
    }
}
