package com.easyschedule.services.interfaces;

import com.easyschedule.models.Specialty;
import org.springframework.boot.configurationprocessor.json.JSONArray;

public interface SpecialtyService {

    Specialty addSpecialty(String name, int year);

    Specialty addSpecialty(String name, int year, JSONArray subjects);

    Specialty getSpecialty(Long id);

    Iterable<Specialty> getAll();

    Specialty updateSpecialty(long id, String name, int year);

    void deleteSpecialty(Long id);

    void deleteAll();
}
