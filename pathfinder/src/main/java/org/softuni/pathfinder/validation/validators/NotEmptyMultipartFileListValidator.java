package org.softuni.pathfinder.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.softuni.pathfinder.validation.anotations.NotEmptyFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class NotEmptyMultipartFileListValidator implements ConstraintValidator<NotEmptyFile, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return false;
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}