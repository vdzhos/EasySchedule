package com.easyschedule.services.implementations;

import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.models.Lesson;
import com.easyschedule.repositories.LessonRepository;
import com.easyschedule.services.interfaces.LessonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public boolean existsById(Long id) {
        return lessonRepository.existsById(id);
    }

    @Override
    @Transactional
    public boolean deleteLesson(Long id) throws NoLessonWithSuchIdFound {
        if(!existsById(id)){
            throw new NoLessonWithSuchIdFound(id);
        }
        lessonRepository.deleteById(id);
        return true;
    }

    @Override
    public Lesson updateLesson(Lesson lesson) throws NoLessonWithSuchIdFound {
        if(!existsById(lesson.getId())) throw new NoLessonWithSuchIdFound(lesson.getId());
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getLesson(Long id) throws NoLessonWithSuchIdFound{
        return lessonRepository.findById(id).orElseThrow(() -> new NoLessonWithSuchIdFound(id));
    }

    @Override
    public List<Lesson> getAll() {
        return (List<Lesson>) lessonRepository.findAll();
    }

}