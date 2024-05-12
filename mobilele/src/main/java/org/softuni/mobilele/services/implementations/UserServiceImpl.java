package org.softuni.mobilele.services.implementations;

import org.modelmapper.ModelMapper;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.domain.entities.User;
import org.softuni.mobilele.domain.entities.UserRole;
import org.softuni.mobilele.repositories.UserRepository;
import org.softuni.mobilele.repositories.UserRoleRepository;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl  implements UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        UserRole userRole = userRoleRepository.findById(userRegisterDto.getRole()).orElseThrow();
        User user = this.mapper.map(userRegisterDto, User.class);

        user.setRole(userRole);
        user.setCreated(new Date());
        user.setActive(true);

        this.userRepository.saveAndFlush(user);
    }
}
