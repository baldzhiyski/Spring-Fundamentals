package bg.softuni.dictionaryapp.validation.validators;

import bg.softuni.dictionaryapp.validation.annotations.DateNotInTheFuture;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class DateNotInTheFutureValidator implements ConstraintValidator<DateNotInTheFuture, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Date is required").addConstraintViolation();
            return false;
        }
        Date currentDate = new Date();

        return !value.after(currentDate);
    }
}