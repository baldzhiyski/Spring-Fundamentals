package org.softuni.mobilele.validation.validators;

import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;
import org.softuni.mobilele.services.UserService;
import org.softuni.mobilele.validation.annotations.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserService userService;// Assume you have a UserService to check for existing usernames

    @Autowired
    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !userService.userByUsernameExists(username);
    }
}