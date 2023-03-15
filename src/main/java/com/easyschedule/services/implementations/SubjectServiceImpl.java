package com.easyschedule.services.implementations;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.exceptions.subject.SubjectNotFoundException;
import com.easyschedule.models.Lesson;
import com.easyschedule.models.Specialty;
import com.easyschedule.models.Subject;
import com.easyschedule.models.SubjectType;
import com.easyschedule.repositories.SubjectRepository;
import com.easyschedule.services.interfaces.LessonService;
import com.easyschedule.services.interfaces.SubjectService;
import com.easyschedule.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepo;
    private final LessonService lessonService;
    private final Utils processor;

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepo.save(subject);
    }

    @Override
    public Subject addSubject(String name, int quantOfGroups, Set<Specialty> specialties) {
        name = processor.processName(name);
        processor.checkSubjectName(name);
        processor.checkQuantOfGroups(quantOfGroups);
        processor.checkQuantOfSpecialties(specialties == null ? 0 : specialties.size());
        Iterable<Subject> subjectsWithSuchName = subjectRepo.findByName(name);
        processor.checkSpecialties(subjectsWithSuchName, specialties);
        return subjectRepo.save(new Subject(name, quantOfGroups, specialties));
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
    public Subject updateSubject(Long id, String name, int quantOfGroups, Set<Specialty> specialties) {
        name = processor.processName(name);
        processor.checkSubjectName(name);
        processor.checkQuantOfGroups(quantOfGroups);
        processor.checkQuantOfSpecialties(specialties == null ? 0 : specialties.size());
        Iterable<Subject> subjectsWithSuchName = subjectRepo.findByNameAndNotId(id, name);
        processor.checkSpecialties(subjectsWithSuchName, specialties);
        String finalName = name;
        return subjectRepo.findById(id).map((subject) -> {
            if (nothingChanged(subject, finalName, quantOfGroups, specialties))
                return subject;

            if (subject.getQuantOfGroups() > quantOfGroups) {
                deleteLessonsWithIncorrectGroups(quantOfGroups, subject.getId());
            }

            subject.setName(finalName);
            subject.setQuantOfGroups(quantOfGroups);
            subject.setSpecialties(specialties);
            return subjectRepo.save(subject);
        }).orElseGet(() -> subjectRepo.save(new Subject(id, finalName, quantOfGroups, specialties)));
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
        SortedSet<Integer> set = new TreeSet<>();
        for (Lesson less : sbj.getLessons())
            set.addAll(less.getIntWeeks());
        return set;
    }

    @Override
    public Set<Integer> getLessonWeeks(Set<Long> ids) {
        SortedSet<Integer> set = new TreeSet<>();
        for (Long id : ids)
            set.addAll(this.getLessonWeeks(id));
        return set;
    }

    private boolean nothingChanged(Subject subject, String name, int quantOfGroups, Set<Specialty> specialties) {
        return subject.getName().equals(name) && subject.getQuantOfGroups() == quantOfGroups
                && subject.getSpecialties().equals(specialties);
    }

    private void deleteLessonsWithIncorrectGroups(int newGroupNum, Long subjectId) {
        Iterable<Lesson> lessons = lessonService.getAll();
        for (Lesson lesson : lessons) {
            if (lesson.getGroup().getType() == SubjectType.SubjectTypeEnum.LECTURE) continue;
            if (lesson.getSubject().getId().equals(subjectId) &&
                    Integer.parseInt(lesson.getGroup().getGroup()) > newGroupNum) {
                try {
                    lessonService.deleteLesson(lesson.getId());
                } catch (NoLessonWithSuchIdFound noLessonWithSuchIdFound) {
                    noLessonWithSuchIdFound.printStackTrace();
                }
            }
        }
    }
}
