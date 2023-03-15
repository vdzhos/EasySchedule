package com.easyschedule.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Specialty implements Comparable<Specialty> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Specialty name must not be blank")
    private String name;

    @Column(nullable = false, name = "specialty_year")
    @Min(1)
    @Max(6)
    private int year;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "specialties", fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();

    public Specialty(Long id, String name, int year) {
        this.name = name;
        this.id = id;
        this.year = year;
    }

    public Specialty(String name, int year) {
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + '-' + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialty specialty)) return false;
        return getYear() == specialty.getYear() &&
                getId().equals(specialty.getId()) &&
                getName().equals(specialty.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }

    @Override
    public int compareTo(Specialty o) {
        int cmp = getName().compareTo(o.getName());
        if (cmp == 0) return getYear() - o.getYear();
        return cmp;
    }
}

