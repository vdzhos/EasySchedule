package com.easyschedule.exceptions.user;

public class UserAccessForbiddenException extends RuntimeException {
    public UserAccessForbiddenException(String reason) {
        super("Access forbidden: " + reason);
    }
}
