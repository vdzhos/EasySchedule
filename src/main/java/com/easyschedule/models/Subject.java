package com.easyschedule.models;

import com.easyschedule.utils.EntityIdResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@NoArgsConstructor
public class Subject implements Comparable<Subject> {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotBlank(message = "Field shouldn't be empty!")
    @NotNull(message = "Mandatory field!")
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    @Min(1)
    @Max(30)
    @NotNull(message = "Mandatory field!")
    private int quantOfGroups;


    @Getter
    @Setter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Teacher.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER)
    private Set<Teacher> teachers;


    @Getter
    @Setter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Specialty.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subjects_specialties",
            joinColumns = @JoinColumn(name = "subject_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "specialty_id", nullable = false)
    )
    @NotNull(message = "Mandatory field!")
    private Set<Specialty> specialties;

    @Getter
    @Setter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Lesson.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    public Subject(String name, int quantOfGroups) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = new HashSet<>();
    }

    public Subject(String name, int quantOfGroups, Set<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = specialties;
    }

    public Subject(long id, String name, int quantOfGroups, Set<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return name + '(' + getSpecialtiesToString() + ')';
    }

    public String getSpecialtiesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (specialties.isEmpty()) return "";
        for (Specialty s : specialties) {
            stringBuilder.append(s).append(", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public String getTeachersToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (teachers.isEmpty()) return "";
        for (Teacher t : teachers) {
            stringBuilder.append(t).append(", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public void addTeacher(Teacher t) {
        teachers.add(t);
    }

    public void addSpecialty(Specialty sp) {
        specialties.add(sp);
    }

    @Override
    public int compareTo(Subject o) {
        int cmp = getName().compareTo(o.getName());
        if (cmp == 0)
            return this.getQuantOfGroups() - o.getQuantOfGroups();
        return cmp;
    }
}

