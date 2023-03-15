package com.easyschedule.services.interfaces;

import com.easyschedule.models.Subject;
import com.easyschedule.models.Teacher;

import java.util.Set;

public interface TeacherService {

    Teacher addTeacher(String name, Set<Subject> subjects);
    Teacher addTeacher(Teacher teacher);
    boolean teacherExistsById(Long id);
    boolean teacherExistsByName(String name);
    boolean deleteTeacher(Long id);
    Teacher updateTeacher(Long id, String name, Set<Subject> subjects) throws Exception;
    Teacher updateTeacher(Teacher teacher) throws Exception;
    Teacher updateTeacherNoCheck(Teacher teacher);
    Teacher getTeacherById(Long id);

    Iterable<Teacher> getTeacherByPartName(String name) throws Exception;
    Teacher getTeacherByName(String name) throws Exception;
    Iterable<Teacher> getAll();

}
