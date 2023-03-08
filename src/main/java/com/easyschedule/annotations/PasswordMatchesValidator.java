package com.easyschedule.annotations;

import com.easyschedule.dtos.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDTO user = (UserDTO) obj;
        if(user.getMatchingPassword() == null)
            return false;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
