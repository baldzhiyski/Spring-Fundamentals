package bg.softuni.exam.vallidation.validators;

import bg.softuni.exam.model.dto.user.LogInDto;
import bg.softuni.exam.model.entity.User;
import bg.softuni.exam.service.UserService;
import bg.softuni.exam.vallidation.annotations.CorrectUser;
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
            return false;
        }

        User user = userService.getUserByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }
}