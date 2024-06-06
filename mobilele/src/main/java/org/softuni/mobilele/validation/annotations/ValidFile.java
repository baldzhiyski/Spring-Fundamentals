package org.softuni.mobilele.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.mobilele.validation.validators.FileNotEmptyValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileNotEmptyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "{file.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long maxSize() default Long.MAX_VALUE; // Maximum file size in bytes

    String[] allowedTypes() default {}; // Allowed MIME types
}
