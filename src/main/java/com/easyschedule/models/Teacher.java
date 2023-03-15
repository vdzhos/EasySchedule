package com.easyschedule.models;

import com.easyschedule.utils.EntityIdResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Teacher implements Comparable<Teacher>{

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotBlank(message = "Field shouldn't be empty!")
    @NotNull
    private String name;

    @Getter
    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Subject.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "subject_id", nullable = false))
    @NotNull
    private Set<Subject> subjects;

    @Getter
    @Setter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Lesson.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Lesson> lessons;

    public Teacher(String name) {
        this.name = name;
        subjects = new HashSet<>();
    }

    public Teacher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher(String name, Set<Subject> subjects) {
        this.subjects = subjects;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getSubjectsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (subjects.isEmpty()) return "";
        for (Subject s : subjects) {
            stringBuilder.append(s).append(", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public boolean hasSubject(Subject s)
    {
        return subjects.contains(s);
    }

    public boolean hasSubject(Long sId) {
        for (Subject s: subjects) {
            if (s.getId().equals(sId))
                return true;
        }
        return false;
    }

    @Override
    public int compareTo(Teacher that) {
        return this.name.compareTo(that.name);
    }

}