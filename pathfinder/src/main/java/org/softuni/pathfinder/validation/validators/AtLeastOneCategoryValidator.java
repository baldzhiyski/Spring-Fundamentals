package org.softuni.pathfinder.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.softuni.pathfinder.validation.anotations.AtLeastOneCategory;
import java.util.List;

public class AtLeastOneCategoryValidator implements ConstraintValidator<AtLeastOneCategory, List<CategoryName>> {
    @Override
    public void initialize(AtLeastOneCategory constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<CategoryName> categories, ConstraintValidatorContext context) {
        return categories != null && !categories.isEmpty();
    }
}
