package org.softuni.mobilele.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mindrot.jbcrypt.BCrypt;
import org.softuni.mobilele.domain.dtos.user.UserLogInDto;
import org.softuni.mobilele.domain.entities.User;
import org.softuni.mobilele.services.UserService;
import org.softuni.mobilele.validation.annotations.CorrectUser;
import org.springframework.beans.factory.annotation.Autowired;
public class CorrectUserValidator implements ConstraintValidator<CorrectUser, UserLogInDto> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(CorrectUser constraintAnnotation) {
        // No initialization required
    }

    @Override
    public boolean isValid(UserLogInDto dto, ConstraintValidatorContext context) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        if (username == null || password == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username and password must not be null")
                    .addConstraintViolation();
            return false;
        }

        if (!userService.userByUsernameExists(username)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("No such username in the database. Please create an account first.")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        User user = userService.getUserByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Incorrect username or password.")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}