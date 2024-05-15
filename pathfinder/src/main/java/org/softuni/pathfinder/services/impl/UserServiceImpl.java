package org.softuni.pathfinder.services.impl;

import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
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
    public void register(UserRegisterDto userRegisterDto) {
        User user = this.mapper.map(userRegisterDto, User.class);
        this.userRepository.saveAndFlush(user);
    }
}
