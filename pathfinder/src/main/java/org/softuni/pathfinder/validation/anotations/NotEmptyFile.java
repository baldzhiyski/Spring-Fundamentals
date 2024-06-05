package org.softuni.pathfinder.validation.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.pathfinder.validation.validators.AtLeastOneCategoryValidator;
import org.softuni.pathfinder.validation.validators.NotEmptyMultipartFileListValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEmptyMultipartFileListValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyFile {
    String message() default "Please select at least one photo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}