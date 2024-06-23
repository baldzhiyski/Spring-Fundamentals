package bg.softuni.exam.vallidation.annotations;
import bg.softuni.exam.vallidation.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PasswordValidator.class })
public @interface PasswordMatch {

    String message() default "Passwords does not match !";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
