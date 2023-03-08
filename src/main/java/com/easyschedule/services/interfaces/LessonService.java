package com.easyschedule.services.interfaces;

import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.models.Lesson;

import java.util.List;

public interface LessonService {

    Lesson addLesson(Lesson lesson);

    boolean deleteLesson(Long id) throws NoLessonWithSuchIdFound;

    Lesson getLesson(Long id) throws NoLessonWithSuchIdFound;

    List<Lesson> getAll();

    Lesson updateLesson(Lesson lesson) throws NoLessonWithSuchIdFound;

    boolean existsById(Long id);

}