package com.easyschedule.services.implementations;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.exceptions.subject.SubjectNotFoundException;
import com.easyschedule.models.Lesson;
import com.easyschedule.models.Subject;
import com.easyschedule.repositories.SubjectRepository;
import com.easyschedule.services.interfaces.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
    @Override
    public Subject updateSubjectNoCheck(Subject subject) {
        return subjectRepo.save(subject);
    }

    @Override
    public List<Lesson> getSubjectLessons(Long subjectId) {
        return getSubject(subjectId).getLessons();
    }

    @Override
    public Set<Integer> getLessonWeeks(Long id) {
        Subject sbj = this.getSubject(id);
        SortedSet<Integer> set = new TreeSet<Integer>();
        for(Lesson less : sbj.getLessons())
            set.addAll(less.getIntWeeks());
        return set;
    }

    @Override
    public Set<Integer> getLessonWeeks(Set<Long> ids) {
        SortedSet<Integer> set = new TreeSet<Integer>();
        for(Long id : ids)
            set.addAll(this.getLessonWeeks(id));
        return set;
    }

}
