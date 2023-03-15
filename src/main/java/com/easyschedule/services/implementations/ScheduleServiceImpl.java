package com.easyschedule.services.implementations;

import com.easyschedule.models.Lesson;
import com.easyschedule.models.Specialty;
import com.easyschedule.models.Subject;
import com.easyschedule.models.Teacher;
import com.easyschedule.services.interfaces.ScheduleService;
import com.easyschedule.services.interfaces.SpecialtyService;
import com.easyschedule.services.interfaces.SubjectService;
import com.easyschedule.services.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;

    @Transactional
    @Override
    public List<Lesson> getTeacherLessons(Long id) throws Throwable {
        List<Lesson> lessons = new ArrayList<Lesson>();
        StreamSupport.stream(this.getTeacherSubjects(id).spliterator(), false)
                .forEach(subj -> lessons
                        .addAll(subjectService.getSubjectLessons(subj.getId())
                                .stream()
                                .filter(less -> less.getTeacher().getId() == id)
                                .collect(Collectors.toList())));
        return lessons;
    }

    @Override
    public List<Lesson> getSpecialtyLessons(Long id) {
        List<Lesson> lessons = new ArrayList<Lesson>();
        StreamSupport.stream(this.getSpecialtySubjects(id).spliterator(), false)
                .forEach(subj -> lessons.addAll(subj.getLessons()));
        return lessons;
    }

    @Override
    public Specialty getSpecialty(Long id) {
        return this.specialtyService.getSpecialty(id);
    }

    @Override
    public Teacher getTeacher(Long id) throws Throwable {
        return this.teacherService.getTeacherById(id);
    }

    @Override
    public Iterable<Subject> getTeacherSubjects(Long id) throws Throwable {
        return (Iterable<Subject>) this.getTeacher(id).getSubjects();
    }

    @Override
    public Iterable<Subject> getSpecialtySubjects(Long id) {
        return this.getSpecialty(id).getSubjects();
    }

    @Override
    public Iterable<Integer> getSubjectLessonsWeeks(Iterable<Subject> subjects) {
        return this.subjectService.getLessonWeeks(StreamSupport
                .stream(subjects.spliterator(), false)
                .map(sub -> sub.getId())
                .collect(Collectors.toSet()));
    }

    public Set<String> getLessonsRooms(List<Lesson> lessons) {
        Set<String> rooms = new HashSet<>();
        lessons.stream().forEach(less -> rooms.add(less.getRoom().getTypeOrName()));
        return rooms;
    }

    @Override
    public Set<Teacher> getTeachersFromSubjects(Iterable<Subject> subjects) {
        Set<Teacher> res = new TreeSet<>();
        subjects.forEach(subj -> res.addAll(subj.getTeachers()));
        return res;
    }
}
