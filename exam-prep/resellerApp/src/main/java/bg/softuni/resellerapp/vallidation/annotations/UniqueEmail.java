package bg.softuni.resellerapp.vallidation.annotations;

import bg.softuni.resellerapp.vallidation.validators.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { UniqueEmailValidator.class })
public @interface UniqueEmail {

    String message() default "Choose another email.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
