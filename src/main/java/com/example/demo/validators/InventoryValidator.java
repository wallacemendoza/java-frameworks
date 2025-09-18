package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryValidator implements ConstraintValidator<ValidInventory, Object> {

    @Override
    public void initialize(ValidInventory constraintAnnotation) {
        // nothing needed
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) return true;

        Integer inv = null;
        Integer min = null;
        Integer max = null;

        if (obj instanceof Part) {
            Part part = (Part) obj;
            inv = part.getInv();
            min = part.getMinInv();
            max = part.getMaxInv();
        } else if (obj instanceof Product) {
            Product product = (Product) obj;
            inv = product.getInv();
            min = product.getMinInv();
            max = product.getMaxInv();
        }

        if (inv == null || min == null || max == null) {
            return true; // skip if values missing
        }

        boolean valid = inv >= min && inv <= max;

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Inventory must be between " + min + " and " + max
            ).addConstraintViolation();
        }

        return valid;
    }
}
