package org.hbrs.se2.project.aldavia.entities;

import java.util.*;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "kenntnisse", schema = "aldavia_new")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kenntnis {
    @Id
    @Column(name = "bezeichnung")
    private String bezeichnung;

    @ManyToMany
    private List<Student> students;

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        if (!this.students.contains(student)) {
            this.students.add(student);
            student.addKenntnis(this);
        }
    }

    public void removeStudent(Student student) {
        if (students == null) {
            return;
        }
        if (this.students.contains(student)) {
            this.students.remove(student);
            student.removeKenntnis(this);
        }
    }
}