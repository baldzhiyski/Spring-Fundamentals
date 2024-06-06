package org.softuni.mobilele.validation.annotations;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.mobilele.validation.validators.UniqueUsernameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "{unique.username}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}