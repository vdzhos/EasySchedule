package com.easyschedule.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String login) {
        super("User with login " + login + " not found");
    }
}