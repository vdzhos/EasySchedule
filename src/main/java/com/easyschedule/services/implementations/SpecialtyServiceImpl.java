package com.easyschedule.services.implementations;

import com.easyschedule.exceptions.specialty.SpecialtyInstanceAlreadyExistsException;
import com.easyschedule.exceptions.specialty.SpecialtyNotFoundException;
import com.easyschedule.models.Specialty;
import com.easyschedule.models.Subject;
import com.easyschedule.repositories.SpecialtyRepository;
import com.easyschedule.services.interfaces.SpecialtyService;
import com.easyschedule.services.interfaces.SubjectService;
import com.easyschedule.utils.Utils;
import com.easyschedule.utils.Values;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SubjectService subjectService;
    private final Utils processor;

    @Override
    public Specialty addSpecialty(String name, int year) {
        name = processor.processName(name);
        processor.checkName(name);
        processor.checkYear(year);
        if (specialtyRepository.existsByNameAndYear(name, year)) {
            throw new SpecialtyInstanceAlreadyExistsException(Values.SPECIALTY_ALREADY_EXISTS);
        }
        return specialtyRepository.save(new Specialty(name, year));
    }

    @Override
    public Specialty addSpecialty(String name, int year, JSONArray subjectIds) {
        Set<Subject> subjects = new HashSet<>();
        for (int i = 0; i < subjectIds.length(); i++) {
            try {
                subjects.add(subjectService.getSubject(subjectIds.getLong(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Specialty specialty = addSpecialty(name, year);
        subjects.forEach(s -> {
            s.addSpecialty(specialty);
            subjectService.updateSubjectNoCheck(s);
        });
        specialty.setSubjects(subjects);
        return specialty;
    }

    @Transactional
    @Override
    public void deleteSpecialty(Long id) {
        if (specialtyRepository.existsById(id)) {
            specialtyRepository.deleteById(id);
            deleteSubjects(id);
        } else {
            throw new SpecialtyNotFoundException(id);
        }
    }

    private void deleteSubjects(Long spId) {
        Iterable<Subject> subjects = subjectService.getAll();
        for (Subject s : subjects) {
            if (s.hasOnlyOneSpecialty() && s.hasSpecialty(spId)) {
                subjectService.deleteSubject(s.getId());
            }
        }
    }

    @Override
    public Specialty updateSpecialty(long id, String name, int year) {
        name = processor.processName(name);
        processor.checkName(name);
        processor.checkYear(year);
        if (specialtyRepository.existsByNameAndYearAndNotId(id, name, year)) {
            throw new SpecialtyInstanceAlreadyExistsException(Values.SPECIALTY_ALREADY_EXISTS);
        }
        String finalName = name;
        return specialtyRepository.findById(id).map((specialty) -> {
            if (nothingChanged(specialty, finalName, year)) {
                return specialty;
            }
            specialty.setName(finalName);
            specialty.setYear(year);
            Specialty s = specialtyRepository.save(specialty);
            return s;
        }).orElseGet(() -> {
            return specialtyRepository.save(new Specialty(id, finalName, year));
        });
    }

    private boolean nothingChanged(Specialty specialty, String name, int year) {
        return specialty.getName().equals(name) && specialty.getYear() == year;
    }

    @Override
    public Iterable<Specialty> getAll() {
        List<Specialty> specialties = (List<Specialty>) specialtyRepository.findAll();
        Collections.sort(specialties);
        return specialties;
    }

    @Override
    public Specialty getSpecialty(Long id) {
        return specialtyRepository.findById(id).orElseThrow(() -> new SpecialtyNotFoundException(id));
    }

    @Override
    public void deleteAll() {
        for (Specialty s : getAll()) {
            specialtyRepository.deleteById(s.getId());
            deleteSubjects(s.getId());
        }
    }
}
