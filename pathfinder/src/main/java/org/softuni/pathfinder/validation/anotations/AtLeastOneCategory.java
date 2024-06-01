package org.softuni.pathfinder.validation.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.pathfinder.validation.validators.AtLeastOneCategoryValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AtLeastOneCategoryValidator.class})
@Documented
public @interface AtLeastOneCategory {
    String message() default "At least one category must be selected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}