package org.softuni.pathfinder.validation.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.pathfinder.validation.validators.FileValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface FileAnnotation {

    String[] contentTypes();

    String message() default "Invalid file content type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
