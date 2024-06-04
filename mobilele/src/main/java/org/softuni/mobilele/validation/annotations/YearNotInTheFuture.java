package org.softuni.mobilele.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.softuni.mobilele.validation.validators.FileNotEmptyValidator;
import org.softuni.mobilele.validation.validators.YearNotInTheFutureValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = YearNotInTheFutureValidator.class)
public @interface YearNotInTheFuture {
    String message() default  "Year should not be in the future !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
