package com.easyschedule.services.interfaces;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.models.Lesson;
import com.easyschedule.models.Subject;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    Subject addSubject(Subject subject);

    boolean deleteSubject(Long id);

    Subject getSubject(Long id);

    List<Subject> getAll();

    Subject updateSubject(Long id, SubjectPutDTO dto);

    Subject updateSubjectNoCheck(Subject subject);

    List<Lesson> getSubjectLessons(Long subjectId);

    Set<Integer> getLessonWeeks(Long id);

    Set<Integer> getLessonWeeks(Set<Long> ids);

}
