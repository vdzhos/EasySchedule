package com.easyschedule.services.interfaces;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.models.Subject;

import java.util.List;

public interface SubjectService {

    Subject addSubject(Subject subject);

    boolean deleteSubject(Long id);

    Subject getSubject(Long id);

    List<Subject> getAll();

    Subject updateSubject(Long id, SubjectPutDTO dto);

}
