package com.trio.java.bikerentapi.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterNowValidator.class)
public @interface AfterNow {
  String message() default "must be today or someday after today";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
