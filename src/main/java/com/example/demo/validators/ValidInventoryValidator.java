package com.example.demo.validators;

import com.example.demo.domain.Part;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidInventoryValidator implements ConstraintValidator<ValidInventory, Part> {

    @Override
    public void initialize(ValidInventory constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        if (part == null) return true;

        Integer min = part.getMinInv();
        Integer max = part.getMaxInv();
        Integer inv = part.getInv();

        // Ignore if values are missing (other validators handle @Min >= 0)
        if (min == null || max == null || inv == null) {
            return true;
        }

        // Rule 1: Min cannot be greater than Max
        if (min > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Minimum inventory cannot be greater than maximum.")
                    .addPropertyNode("minInv").addConstraintViolation();
            return false;
        }

        // Rule 2: Inventory must be between Min and Max
        if (inv < min || inv > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Inventory must be between " + min + " and " + max + ".")
                    .addPropertyNode("inv").addConstraintViolation();
            return false;
        }

        return true; // ✅ Valid if all checks pass
    }
}
