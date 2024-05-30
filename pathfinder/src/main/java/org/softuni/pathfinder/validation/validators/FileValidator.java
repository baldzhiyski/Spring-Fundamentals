package org.softuni.pathfinder.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.softuni.pathfinder.validation.anotations.FileAnnotation;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileValidator implements ConstraintValidator<FileAnnotation, MultipartFile> {

    private List<String> contentTypes;

    @Override
    public void initialize(FileAnnotation constraintAnnotation) {
        this.contentTypes = Arrays.asList(constraintAnnotation.contentTypes());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false; // Consider empty files as invalid
        }

        return contentTypes.contains(file.getContentType());
    }
}