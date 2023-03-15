package com.easyschedule.services.interfaces;

import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.models.Lesson;
import com.easyschedule.models.SubjectType;

import java.time.DayOfWeek;
import java.util.List;

public interface LessonService {

    Lesson addLesson(Lesson lesson);

    Lesson addLesson(Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek of) throws Exception;

    boolean deleteLesson(Long id) throws NoLessonWithSuchIdFound;

    Lesson getLesson(Long id) throws NoLessonWithSuchIdFound;

    List<Lesson> getAll();

    Lesson updateLesson(Lesson lesson) throws NoLessonWithSuchIdFound;

    Lesson updateLesson(Long id, Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek of) throws Exception;

    boolean existsById(Long id);

}