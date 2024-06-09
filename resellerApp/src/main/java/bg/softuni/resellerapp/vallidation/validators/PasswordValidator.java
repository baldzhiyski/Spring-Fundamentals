package bg.softuni.resellerapp.vallidation.validators;

import bg.softuni.resellerapp.model.dtos.user.UserRegisterDto;
import bg.softuni.resellerapp.vallidation.annotations.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatch, UserRegisterDto> {
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegisterDto userRegisterBindingModel, ConstraintValidatorContext context) {

        final String password = userRegisterBindingModel.getPassword();
        final String confirmPassword = userRegisterBindingModel.getConfirmPassword();

        if (password == null && confirmPassword == null) {

            return true;
        } else {

            boolean passwordMatch = password != null && password.equals(confirmPassword);

            if (!passwordMatch) {

                HibernateConstraintValidatorContext hibernateContext =
                        context.unwrap(HibernateConstraintValidatorContext.class);

                hibernateContext
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }

            return passwordMatch;
        }
    }
}
