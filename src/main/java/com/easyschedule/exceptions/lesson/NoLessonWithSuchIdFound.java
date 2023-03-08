package com.easyschedule.exceptions.lesson;

public class NoLessonWithSuchIdFound extends Exception {

    public NoLessonWithSuchIdFound(Long id) {
        super("Lesson with id '"+ id +"' not found!");
    }

}