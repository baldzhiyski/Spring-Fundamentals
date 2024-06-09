package bg.softuni.resellerapp.service.impl;

import bg.softuni.resellerapp.model.User;
import bg.softuni.resellerapp.model.dtos.UserLogInDto;
import bg.softuni.resellerapp.model.dtos.UserRegisterDto;
import bg.softuni.resellerapp.repository.UserRepository;
import bg.softuni.resellerapp.service.UserService;
import bg.softuni.resellerapp.util.CurrentLoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private CurrentLoggedUser currentLoggedUser;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CurrentLoggedUser currentLoggedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.currentLoggedUser = currentLoggedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isLogged() {
        return currentLoggedUser.isLogged();
    }

    @Override
    public CurrentLoggedUser getLoggedUser() {
        return null;
    }

    @Override
    public boolean logIn(UserLogInDto userLogInDto) {
        Optional<User> byUsername = this.userRepository.findByUsername(userLogInDto.getUsername());
        if(byUsername.isEmpty()){
            return false;
        }
        boolean isMatch = this.passwordEncoder.matches(userLogInDto.getPassword(), byUsername.get().getPassword());
        if(!isMatch){
            return false;
        }

        currentLoggedUser.setLogged(true);
        currentLoggedUser.setUsername(userLogInDto.getUsername());

        return true;
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void logOut() {

    }

    @Override
    public boolean userByUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean usernameByEmailExists(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
