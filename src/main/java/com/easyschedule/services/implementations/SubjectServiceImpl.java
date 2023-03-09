package com.easyschedule.services.implementations;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.exceptions.subject.SubjectNotFoundException;
import com.easyschedule.models.Subject;
import com.easyschedule.repositories.SubjectRepository;
import com.easyschedule.services.interfaces.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepo;


    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepo.save(subject);
    }

    @Override
    @Transactional
    public boolean deleteSubject(Long id) {
        getSubject(id);
        subjectRepo.deleteById(id);
        return true;
    }

    @Override
    public Subject getSubject(Long id) {
        return subjectRepo.findById(id).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    @Override
    public List<Subject> getAll() {
        return (List<Subject>) subjectRepo.findAll();
    }

    @Override
    @Transactional
    public Subject updateSubject(Long id, SubjectPutDTO dto) {
        Subject subject = getSubject(id);
        subject.setName(dto.getName());
        subject.setQuantOfGroups(dto.getQuantOfGroups());
        return subjectRepo.save(subject);
    }
}
