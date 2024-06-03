package org.softuni.mobilele.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.softuni.mobilele.validation.annotations.ValidFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;


public class FileNotEmptyValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private long maxSize;
    private String[] allowedTypes;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File must not be empty.")
                    .addConstraintViolation();
            return false;
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must not exceed " + maxSize + " bytes.")
                    .addConstraintViolation();
            return false;
        }

        if (allowedTypes.length > 0 && !Arrays.asList(allowedTypes).contains(file.getContentType())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File type must be one of: " + String.join(", ", allowedTypes) + ".")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
