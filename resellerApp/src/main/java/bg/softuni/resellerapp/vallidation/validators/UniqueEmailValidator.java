package bg.softuni.resellerapp.vallidation.validators;

import bg.softuni.resellerapp.service.UserService;
import bg.softuni.resellerapp.vallidation.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private String message;
    private final UserService userService;

    public UniqueEmailValidator (UserService userService) {

        this.userService = userService;
    }

    @Override
    public void initialize (UniqueEmail constraintAnnotation) {

        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid (String value, ConstraintValidatorContext context) {

        if (value.isBlank()) {
            // Set custom message when the username is blank
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email is required").addConstraintViolation();
            return false;

        } else {

            return !userService.usernameByEmailExists(value);
        }
    }

    private void replaceDefaultConstraintViolation (ConstraintValidatorContext context, String message) {

        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
