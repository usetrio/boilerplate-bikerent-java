package com.trio.java.bikerentapi.validation;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AfterNowValidator implements ConstraintValidator<AfterNow, LocalDate> {

  @Override
  public boolean isValid(LocalDate localDate,
                         ConstraintValidatorContext constraintValidatorContext) {
    return localDate == null || !localDate.isBefore(LocalDate.now());
  }
}
