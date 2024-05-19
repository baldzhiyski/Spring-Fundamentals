package org.softuni.pathfinder.utils;

import lombok.Getter;
import lombok.Setter;
import org.softuni.pathfinder.domain.entities.Role;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Set;

@Component
@SessionScope
@Getter
@Setter
public class LoggedInUser {
    private String fullName;
    private Long id;

    private String username;
    private Integer age;
    private Set<Role> roles;
    private Level level;
    private boolean isLogged;
}
