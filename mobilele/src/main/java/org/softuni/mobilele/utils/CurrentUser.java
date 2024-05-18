package org.softuni.mobilele.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Getter
@Setter
public class CurrentUser {

    private String firstName;
    private String lastName;
    private Long id;

    private boolean isLogged;
}
