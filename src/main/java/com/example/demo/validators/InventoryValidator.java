package com.example.demo.validators;

import com.example.demo.domain.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryValidator implements ConstraintValidator<ValidInventory, Part> {

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        if (part == null) {
            return true; // Don’t validate null objects
        }

        Integer min = part.getMinInv();
        Integer max = part.getMaxInv();

        // Allow validation to pass if min/max are not set
        if (min == null || max == null) {
            return true;
        }

        return part.getInv() >= min && part.getInv() <= max;
    }
}
