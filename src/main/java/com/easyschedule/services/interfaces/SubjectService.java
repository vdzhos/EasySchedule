package com.easyschedule.services.interfaces;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.exceptions.subject.SubjectNotFoundException;
import com.easyschedule.models.Subject;

import java.util.List;

public interface SubjectService {

    Subject addSubject(Subject subject);

    boolean deleteSubject(Long id) throws SubjectNotFoundException;

    Subject getSubject(Long id) throws SubjectNotFoundException;

    List<Subject> getAll();

    Subject updateSubject(Long id, SubjectPutDTO dto) throws SubjectNotFoundException ;

}
