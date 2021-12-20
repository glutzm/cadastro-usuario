package com.example.springbackend.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PisValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PIS {
    String message() default "PIS inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
