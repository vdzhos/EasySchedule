package com.easyschedule.services.interfaces;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.models.Lesson;
import com.easyschedule.models.Specialty;
import com.easyschedule.models.Subject;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    Subject addSubject(Subject subject);

    Subject addSubject(String name, int quantOfGroups, Set<Specialty> specialties);

    boolean deleteSubject(Long id);

    Subject getSubject(Long id);

    List<Subject> getAll();

    Subject updateSubject(Long id, SubjectPutDTO dto);

    Subject updateSubject(Long id, String name, int quantOfGroups, Set<Specialty> specialties);

    Subject updateSubjectNoCheck(Subject subject);

    List<Lesson> getSubjectLessons(Long subjectId);

    Set<Integer> getLessonWeeks(Long id);

    Set<Integer> getLessonWeeks(Set<Long> ids);

}
