package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "taetigkeitsfelder", schema = "aldavia_new")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Taetigkeitsfeld {


    @Id
    @Column(name = "bezeichnung", unique = true, nullable = false)
    private String bezeichnung;

    // taetigkeitsfeld_hat_studenten

    @ManyToMany(mappedBy = "taetigkeitsfelder")
    private List<Student> students;

    public Taetigkeitsfeld addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        if (!this.students.contains(student)) {
            this.students.add(student);
            student.addTaetigkeitsfeld(this);
        }
        return this;
    }

    public Taetigkeitsfeld removeStudent(Student student) {
        if (students == null) {
            return this;
        }
        if (this.students.contains(student)) {
            this.students.remove(student);
            student.removeTaetigkeitsfeld(this);
        }
        return this;
    }

    // taetigkeitsfeld_hat_stellenanzeigen

    @ManyToMany(mappedBy = "taetigkeitsfelder")
    private List<Stellenanzeige> stellenanzeigen;

    public void addStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeigen == null) {
            stellenanzeigen = new ArrayList<>();
        }
        if (!this.stellenanzeigen.contains(stellenanzeige)) {
            this.stellenanzeigen.add(stellenanzeige);
            stellenanzeige.addTaetigkeitsfeld(this);
        }
    }

    public void removeStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeigen == null) {
            return;
        }
        if (this.stellenanzeigen.contains(stellenanzeige)) {
            this.stellenanzeigen.remove(stellenanzeige);
            stellenanzeige.removeTaetigkeitsfeld(this);
        }
    }

    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taetigkeitsfeld that = (Taetigkeitsfeld) o;
        return Objects.equals(bezeichnung, that.bezeichnung) && Objects.equals(students, that.students) && Objects.equals(stellenanzeigen, that.stellenanzeigen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, students, stellenanzeigen);
    }
}