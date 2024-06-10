package bg.softuni.dictionaryapp.validation.validators;

import bg.softuni.dictionaryapp.validation.annotations.YearNotInTheFuture;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearNotInTheFutureValidator implements ConstraintValidator<YearNotInTheFuture, Year> {
    @Override
    public boolean isValid(Year value, ConstraintValidatorContext constraintValidatorContext) {
        if(value==null){
            return true;
        }
        int currentYear = Year.now().getValue();

        return value.getValue()<=currentYear;
    }
}
