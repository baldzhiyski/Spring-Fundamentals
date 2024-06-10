package bg.softuni.dictionaryapp.validation.annotations;

import bg.softuni.dictionaryapp.validation.validators.DateNotInTheFutureValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateNotInTheFutureValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateNotInTheFuture {
    String message() default "Date should be in the present or past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}