package org.softuni.pathfinder.services.impl;

import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.Role;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.domain.entities.enums.UserRole;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;

    private User logged;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.logged = null;
    }

    @Override
    public boolean userByUsernameExists(String username){
        return  this.userRepository.getUserByUsername(username).isPresent();
    }

    @Override
    public boolean usernameByEmailExists(String email){
        return this.userRepository.getUserByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        // TODO : Set the blanks columns to something
        User mapped = this.mapper.map(userRegisterDto, User.class);
        Role adminRole = new Role(UserRole.ADMIN);
        Role userRole = new Role(UserRole.USER);
        if(userRegisterDto.getUsername() == "admin" && userRegisterDto.getPassword() == "adminPass"){
            mapped.setLevel(Level.ADVANCED);
            mapped.getRoles().add(adminRole);
        }
        mapped.getRoles().add(userRole);

        this.logged = mapped;

        this.userRepository.saveAndFlush(mapped);
    }

    @Override
    public void login(UserLogInDto userLogInDto) {
        // TODO
    }
}
