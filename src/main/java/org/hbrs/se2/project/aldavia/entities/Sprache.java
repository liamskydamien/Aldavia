package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sprachen", schema = "aldavia_new")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sprache {

    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "bezeichnung")
    private String bezeichnung;

    @Basic
    @Column(name = "level")
    private String level;

    // sprache_hat_student

    @ManyToMany(mappedBy = "sprachen")
    private List<Student> students;

    public Sprache addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        if (!this.students.contains(student)) {
            this.students.add(student);
            student.addSprache(this);
        }
        return this;
    }

    public Sprache removeStudent(Student student) {
        if (students == null) {
            return this;
        }
        if (this.students.contains(student)) {
            this.students.remove(student);
            student.removeSprache(this);
        }
        return this;
    }

    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprache sprache = (Sprache) o;
        return id == sprache.id && Objects.equals(bezeichnung, sprache.bezeichnung) && Objects.equals(level, sprache.level) && Objects.equals(students, sprache.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bezeichnung, level, students);
    }
}