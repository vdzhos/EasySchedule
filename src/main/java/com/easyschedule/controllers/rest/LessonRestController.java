package com.easyschedule.controllers.rest;

import com.easyschedule.models.Lesson;
import com.easyschedule.services.interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonRestController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok(lessonService.getLesson(id));
    }

    @PostMapping
    public ResponseEntity<Lesson> addLesson(@Valid @RequestBody Lesson lesson) {
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
