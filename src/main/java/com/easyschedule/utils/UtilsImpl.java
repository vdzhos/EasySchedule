package com.easyschedule.utils;

import com.easyschedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.easyschedule.exceptions.specialty.SpecialtyIllegalArgumentException;

public class UtilsImpl implements Utils {

    @Override
    public String processName(String name) {
        return name.replaceAll("\\s+", " ").trim();
    }

    @Override
    public boolean isInvalidName(String name) {
        return name.isEmpty();
    }

    @Override
    public void checkName(String name) {
        if (isInvalidName(name)) {
            throw new InvalidSpecialtyNameException(Values.INVALID_SPECIALTY_NAME);
        }
    }

    @Override
    public void checkYear(int year) {
        if (year > Values.MAX_YEAR || year < Values.MIN_YEAR) {
            throw new SpecialtyIllegalArgumentException("Specialty year = " + year + " is out of bounds " + Values.MIN_YEAR + " - " + Values.MAX_YEAR);
        }
    }
}
