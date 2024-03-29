package com.easyschedule.exceptions.teacher;

public class TeacherAlreadyExistsException extends RuntimeException {
    public TeacherAlreadyExistsException(String name) {
        super("Teacher with name =\"" + name + "\" already exists!");
    }
}
