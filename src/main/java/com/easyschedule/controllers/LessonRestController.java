package com.easyschedule.controllers;

import com.easyschedule.models.Lesson;
import com.easyschedule.services.interfaces.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonRestController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/all")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok(lessonService.getLesson(id));
    }

    @PostMapping
    public ResponseEntity<Lesson> addLesson(@Valid @RequestBody Lesson lesson){
        return new ResponseEntity<>(lessonService.addLesson(lesson), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson lesson) throws Exception {
        lesson.setId(id);
        return ResponseEntity.ok(lessonService.updateLesson(lesson));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable(value = "id") Long id) throws Exception {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok("Lesson with id = " + id + " deleted");
    }

}
