package com.example.springbackend.validators;

import com.example.springbackend.util.PisFieldValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PisValidator implements ConstraintValidator<PIS, String> {

    @Override
    public void initialize(PIS constraintAnnotation) {
    }

    @Override
    public boolean isValid(
            String pisField,
            ConstraintValidatorContext context
    ) {
        return pisField != null && PisFieldValidator.validate(pisField);
    }
}
