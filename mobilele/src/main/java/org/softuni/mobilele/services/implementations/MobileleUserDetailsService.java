package org.softuni.mobilele.services.implementations;

import org.softuni.mobilele.domain.entities.UserRole;
import org.softuni.mobilele.domain.entities.enums.Role;
import org.softuni.mobilele.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class MobileleUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MobileleUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private UserDetails map(org.softuni.mobilele.domain.entities.User userEntity) {
        return User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(map(userEntity.getRole()))
                .build();
    }

    private static GrantedAuthority map(UserRole userRoleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getRole().name()
        );
    }
}
