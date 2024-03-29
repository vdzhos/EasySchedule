package com.easyschedule.annotations;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
