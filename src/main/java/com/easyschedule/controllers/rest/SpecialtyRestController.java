package com.easyschedule.controllers.rest;

import com.easyschedule.exceptions.specialty.IncorrectRequestBodyException;
import com.easyschedule.models.Specialty;
import com.easyschedule.services.interfaces.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyRestController {

    private final SpecialtyService specialtyService;

    @GetMapping
    public Iterable<Specialty> getAllSpecialties() {
        return specialtyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialty(@Min(1) @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(specialtyService.getSpecialty(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Specialty> addSpecialty(@RequestBody String json) {
        try {
            JSONObject specialty = new JSONObject(json);
            String name = specialty.getString("name");
            int year = specialty.getInt("year");
            JSONArray array = specialty.getJSONArray("subjects");
            return ResponseEntity.ok(specialtyService.addSpecialty(name, year, array));
        } catch (JSONException e) {
            throw new IncorrectRequestBodyException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@Valid @RequestBody Specialty newSpecialty, @Min(1) @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(id, newSpecialty.getName(), newSpecialty.getYear()));
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable(name = "id") @Min(1) Long id) {
        specialtyService.deleteSpecialty(id);
    }

    @DeleteMapping
    public void deleteAll() {
        specialtyService.deleteAll();
    }
}