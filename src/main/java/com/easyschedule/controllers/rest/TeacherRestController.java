package com.easyschedule.controllers.rest;

import com.easyschedule.exceptions.teacher.TeacherNotFoundException;
import com.easyschedule.models.Teacher;
import com.easyschedule.services.interfaces.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherRestController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok((List<Teacher>) teacherService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@Valid @RequestBody Teacher teacher) {
        return new ResponseEntity<>(teacherService.addTeacher(teacher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody Teacher teacher) throws Exception {
        teacher.setId(id);
        return ResponseEntity.ok(teacherService.updateTeacher(teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable(value = "id") Long id) throws TeacherNotFoundException {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher with id = " + id + " deleted");
    }

}