package com.easyschedule.services.implementations;

import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.exceptions.teacher.TeacherAlreadyExistsException;
import com.easyschedule.exceptions.teacher.TeacherNotFoundException;
import com.easyschedule.models.Lesson;
import com.easyschedule.models.Subject;
import com.easyschedule.models.Teacher;
import com.easyschedule.repositories.TeacherRepository;
import com.easyschedule.services.interfaces.LessonService;
import com.easyschedule.services.interfaces.TeacherService;
import com.easyschedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private Utils processor;


    @Override
    public Teacher addTeacher(String name, Set<Subject> subjects) {
        name = processor.processName(name);
        processor.checkTeacherName(name);
        processor.checkTeachersSubjects(subjects);
        if(teacherRepository.existsByName(name)){
            throw new RuntimeException(name);
        }
        return teacherRepository.save(new Teacher(name, subjects));
    }

    @Override
    public Teacher addTeacher(Teacher teacher){
        return addTeacher(teacher.getName(), teacher.getSubjects());
    }

    @Override
    public boolean teacherExistsById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public boolean teacherExistsByName(String name) {
        return teacherRepository.existsByName(name);
    }

    @Transactional
    @Override
    public boolean deleteTeacher(Long id) {
        if(!teacherExistsById(id)) throw new TeacherNotFoundException("Teacher with id \""+id+"\" not found!");
        teacherRepository.deleteById(id);
        return true;
    }


    @Override
    public Teacher updateTeacher(Long id, String name, Set<Subject> subjects) throws Exception{
        name = processor.processName(name);
        processor.checkTeacherName(name);
        processor.checkTeachersSubjects(subjects);
        if(teacherRepository.existsByNameAndNotId(id,name)){
            throw new TeacherAlreadyExistsException(name);
        }
        String finalName = name;
        return teacherRepository.findById(id).map((teacher) -> {
            teacher.setName(finalName);
            teacher.setSubjects(subjects);
            Set<Long> ids = subjects.stream().map(Subject::getId).collect(Collectors.toSet());
            deleteLessonsWithIncorrectSubjects(ids, teacher.getId());
            return teacherRepository.save(teacher);
        }).orElseGet(() -> teacherRepository.save(new Teacher(finalName, subjects)));
    }


    private void deleteLessonsWithIncorrectSubjects(Set<Long> ids, Long teacherId) {
        Iterable<Lesson> lessons = lessonService.getAll();
        for (Lesson lesson: lessons) {
            if (lesson.getTeacher().getId().equals(teacherId) && !ids.contains(lesson.getSubject().getId())) {
                try {
                    lessonService.deleteLesson(lesson.getId());
                } catch (NoLessonWithSuchIdFound noLessonWithSuchIdFound) {
                    noLessonWithSuchIdFound.printStackTrace();
                }
            }
        }
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) throws Exception {
        return updateTeacher(teacher.getId(), teacher.getName(), teacher.getSubjects());
    }

    @Override
    public Teacher updateTeacherNoCheck(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException("Teacher with id \""+id+"\" not found!"));
    }

    @Override
    public Teacher getTeacherByName(String name) throws Exception {
        Iterable<Teacher> res = teacherRepository.findByName(name);
        if (!res.iterator().hasNext()) throw new TeacherNotFoundException("Teacher with name '"+ name +"' has not been found!");
        return res.iterator().next();
    }

    @Override
    public Iterable<Teacher> getTeacherByPartName(String name) throws Exception {
        return teacherRepository.findByPartName(name);
    }

    @Override
    public Iterable<Teacher> getAll() {
        return teacherRepository.findAll();
    }
}