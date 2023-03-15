package com.easyschedule.services.interfaces;

import com.easyschedule.models.Lesson;
import com.easyschedule.models.Specialty;
import com.easyschedule.models.Subject;
import org.springframework.boot.configurationprocessor.json.JSONArray;

import java.util.List;

public interface SpecialtyService {

    Specialty addSpecialty(String name, int year);

    Specialty addSpecialty(String name, int year, JSONArray subjects);

    Specialty getSpecialty(Long id);

    Iterable<Specialty> getAll();

    Specialty updateSpecialty(long id, String name, int year);

    void deleteSpecialty(Long id);

    void deleteAll();

    Iterable<Subject> getSpecialtySubjects(Long specialtyId);

    List<Lesson> getSpecialtyLessons(Long id);
}
