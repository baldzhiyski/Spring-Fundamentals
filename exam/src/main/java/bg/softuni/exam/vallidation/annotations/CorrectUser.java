package bg.softuni.exam.vallidation.annotations;

import bg.softuni.exam.vallidation.validators.CorrectUserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = CorrectUserValidator.class)
@Documented
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectUser {
    String message() default "Invalid username or password !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}