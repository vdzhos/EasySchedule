package com.easyschedule.controllers;

import com.easyschedule.dtos.SubjectPutDTO;
import com.easyschedule.models.Subject;
import com.easyschedule.services.interfaces.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> addSubject(@Valid @RequestBody Subject subject) {
        return new ResponseEntity<>(subjectService.addSubject(subject), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubject(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject with id = " + id + " deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectPutDTO dto) {
        return ResponseEntity.ok(subjectService.updateSubject(id, dto));
    }


}
