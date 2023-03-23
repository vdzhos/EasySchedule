package com.easyschedule.services.interfaces;

import com.easyschedule.models.Lesson;
import com.easyschedule.models.Specialty;
import com.easyschedule.models.Subject;
import com.easyschedule.models.Teacher;

import java.util.List;
import java.util.Set;

public interface ScheduleService {

    List<Lesson> getTeacherLessons(Long id) throws Throwable;

    List<Lesson> getSpecialtyLessons(Long id);

    Specialty getSpecialty(Long id);

    Teacher getTeacher(Long id) throws Throwable;

    Iterable<Subject> getTeacherSubjects(Long id) throws Throwable;

    Iterable<Subject> getSpecialtySubjects(Long id);

    Iterable<Integer> getSubjectLessonsWeeks(Iterable<Subject> subjects);

    Set<String> getLessonsRooms(List<Lesson> list);

    Set<Teacher> getTeachersFromSubjects(Iterable<Subject> subjects);

    //filters

    //filters

    List<Lesson> getLessonsFromSubjects(Iterable<Subject> subjects);

    List<Lesson> getSubjectLessons(Long id);

    List<Lesson> filterLessonsByWeek(List<Lesson> list, int week);

    List<Lesson> filterLessonsByRoom(List<Lesson> list, String room);

}
