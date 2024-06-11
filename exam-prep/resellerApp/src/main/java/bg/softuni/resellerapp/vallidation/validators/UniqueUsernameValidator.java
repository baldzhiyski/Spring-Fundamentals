package bg.softuni.resellerapp.vallidation.validators;


import bg.softuni.resellerapp.service.UserService;
import bg.softuni.resellerapp.vallidation.annotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;// Assume you have a UserService to check for existing usernames

    @Autowired
    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(username.isBlank()){
            // Set custom message when the username is blank
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username is required").addConstraintViolation();
            return false;
        }
        return !this.userService.userByUsernameExists(username);
    }
}
