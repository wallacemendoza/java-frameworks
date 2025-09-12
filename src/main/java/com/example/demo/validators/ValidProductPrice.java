package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceProductValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductPrice {
    String message() default "Price of the product must be greater than or equal to the sum of its parts.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
