package com.easyschedule.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class RoleCodeValidator implements ConstraintValidator<RoleCodeMatches, String> {

    @Value("${custom.admin-code}")
    private String adminCode;

    @Value("${custom.user-code}")
    private String userCode;

    @Override
    public void initialize(RoleCodeMatches constraintAnnotation) {}

    @Override
    public boolean isValid(String rolecode, ConstraintValidatorContext context) {
        return rolecode.equals(adminCode) || rolecode.equals(userCode);
    }

}
