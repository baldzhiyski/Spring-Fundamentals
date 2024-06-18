package org.softuni.pathfinder.services.impl;

import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.user.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.user.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.Role;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.domain.entities.enums.UserRole;
import org.softuni.pathfinder.repositories.RoleRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

        // TODO : How should we actually set the roles , which should be admins , which not

        User mapped = this.mapper.map(userRegisterDto, User.class);
        mapped.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        Set<Role> roles = new HashSet<>();

        // TODO : Handle the table for roles is empty
        
        Role adminRole = this.roleRepository.getById(1L);
        Role userRole = this.roleRepository.getById(3L);
        Role moderatorRole = this.roleRepository.getById(2L);

        // Register a new admin in the system
        // We can change here the properties and give them to people to use so they can become admins when creating account
        if(userRegisterDto.getUsername().equals("admin123456") && userRegisterDto.getPassword().equals("1234567")){
            mapped.setLevel(Level.ADVANCED);
            roles.add(adminRole);
        }
        roles.add(userRole);
        mapped.setRoles(roles);

        if(mapped.getAge() < 20){
            mapped.setLevel(Level.BEGINNER);
        }else {
            mapped.setLevel(Level.INTERMEDIATE);
        }

        this.userRepository.saveAndFlush(mapped);
    }


    @Override
    public UserLogInDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserLogInDto userLogInDto = new UserLogInDto();
        userLogInDto.setUsername(currentUsername);
        return userLogInDto;
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.getUserByUsername(username).orElseThrow();
    }
}
