package com.easyschedule.exceptions.user;

public class UserAccessForbiddenException extends Exception {
    public UserAccessForbiddenException(String reason) {
        super("Access forbidden: " + reason);
    }
}
