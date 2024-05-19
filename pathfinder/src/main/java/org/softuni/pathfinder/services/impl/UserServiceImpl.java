package org.softuni.pathfinder.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.Role;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.domain.entities.enums.UserRole;
import org.softuni.pathfinder.repositories.RoleRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;
import org.softuni.pathfinder.utils.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
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

    private BCrypt passwordEncoder;
    private LoggedInUser logged;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, RoleRepository roleRepository,LoggedInUser logged) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.logged = logged;
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

        Set<Role> roles = new HashSet<>();

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
    public void login(UserLogInDto userLogInDto) {
        User user = this.userRepository.getUserByUsername(userLogInDto.getUsername())
                .orElseThrow();

        this.logged.setId(user.getId());
        this.logged.setLogged(true);
        this.logged.setFullName(user.getFullName());
        this.logged.setUsername(user.getUsername());
        this.logged.setAge(user.getAge());
        this.logged.setRoles(user.getRoles());
        this.logged.setLevel(user.getLevel());

    }

    @Override
    public boolean checkPasswordCorrectForTheUsername(UserLogInDto userLogInDto) {
        Optional<User> optionalUser = userRepository.getUserByUsername(userLogInDto.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashedPassword = user.getPassword(); // Get hashed password from the database
            // Use BCryptPasswordEncoder to verify if the provided password matches the hashed password
            return BCrypt.checkpw(userLogInDto.getPassword(), hashedPassword);

        } else {
            return false;
        }
    }

    @Override
    public boolean logOut() {
        if(!this.logged.isLogged()){
            return false;
        }else{
            this.logged.setLogged(false);
            return true;
        }
    }

    @Override
    public boolean isLoggedIn() {
        return this.logged.isLogged();
    }

    @Override
    public boolean isAdmin() {
        if (!logged.isLogged()) {
            return false;
        }

        User user = userRepository.findById(logged.getId()).orElse(null);
        return (user != null ? user.getRoles().stream().filter(role -> role.getName().equals(UserRole.valueOf("ADMIN"))).count() : 0) > 0;
    }

    @Override
    public LoggedInUser getLoggedInUser() {
        return this.logged;
    }
}
