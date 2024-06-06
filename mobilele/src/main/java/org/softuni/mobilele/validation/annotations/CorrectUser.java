package org.softuni.mobilele.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.mobilele.validation.validators.CorrectUserValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = CorrectUserValidator.class)
@Documented
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectUser {
    String message() default "{correct.user.data}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}