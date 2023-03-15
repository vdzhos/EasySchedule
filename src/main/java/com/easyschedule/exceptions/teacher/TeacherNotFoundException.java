package com.easyschedule.exceptions.teacher;

public class TeacherNotFoundException extends RuntimeException {

    public TeacherNotFoundException(String msg) {
        super(msg);
    }
}
