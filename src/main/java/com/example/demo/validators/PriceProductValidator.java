package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceProductValidator implements ConstraintValidator<ValidProductPrice, Product> {

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product == null || product.getParts() == null) {
            return true; // let @NotNull handle nulls
        }

        double sumPartsPrice = 0.0;
        for (Part part : product.getParts()) {
            sumPartsPrice += part.getPrice();
        }

        return product.getPrice() >= sumPartsPrice;
    }
}
