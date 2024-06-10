package bg.softuni.dictionaryapp.validation.validators;

import bg.softuni.dictionaryapp.model.User;
import bg.softuni.dictionaryapp.model.dtos.user.LogInDto;
import bg.softuni.dictionaryapp.service.UserService;
import bg.softuni.dictionaryapp.validation.annotations.CorrectUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class CorrectUserValidator implements ConstraintValidator<CorrectUser, LogInDto> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(CorrectUser constraintAnnotation) {
        // No initialization required
    }

    @Override
    public boolean isValid(LogInDto dto, ConstraintValidatorContext context) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        if(username.isBlank() && password.isBlank()){
            context.disableDefaultConstraintViolation();
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