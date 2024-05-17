package org.softuni.pathfinder.services.impl;

import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.Role;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.repositories.RoleRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;
    private RoleRepository roleRepository;

    private User logged;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
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
        User mapped = this.mapper.map(userRegisterDto, User.class);

        Set<Role> roles = new HashSet<>();

        Role adminRole = this.roleRepository.getById(1L);
        Role userRole = this.roleRepository.getById(2L);

        // Register a new admin in the system
        // We can change here the properties and give them to people to use so they can become admins when creating account
        if(userRegisterDto.getUsername() == "admin" && userRegisterDto.getPassword() == "adminPass"){
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
    public void login(UserLogInDto userLogInDto) {
        User user = this.userRepository.getUserByUsername(userLogInDto.getUsername())
                .orElseThrow();

        this.logged = user;

    }

    @Override
    public boolean checkPasswordCorrectForTheUsername(UserLogInDto userLogInDto) {
        User user = this.userRepository.getUserByUsername(userLogInDto.getUsername()).get();

        return user.getPassword().equals(userLogInDto.getPassword());
    }
}
