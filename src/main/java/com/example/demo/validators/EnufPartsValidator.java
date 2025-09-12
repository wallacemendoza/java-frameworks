package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class EnufPartsValidator implements ConstraintValidator<ValidEnufParts, Product> {

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product == null) {
            return true; // nothing to validate
        }

        Set<Part> parts = product.getParts();

        // If no parts are linked yet, skip validation
        if (parts == null || parts.isEmpty()) {
            return true;
        }

        // ✅ Sum of all part inventories
        int totalAvailable = parts.stream()
                .mapToInt(Part::getInv)
                .sum();

        // If product inventory exceeds total parts inventory → fail
        if (product.getInv() > totalAvailable) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Product inventory cannot exceed total available parts (" + totalAvailable + ")."
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
