package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "qualifikationen", schema = "aldavia_new")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Qualifikation {
    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "bezeichnung")
    private String bezeichnung;

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;

    @Basic
    @Column(name = "bereich")
    private String bereich;

    @Basic
    @Column(name = "institution")
    private String institution;

    @Basic
    @Column(name = "beschaftigungsverhaltnis")
    private String beschaftigungsverhaltnis;

    @Basic
    @Column(name = "von")
    private LocalDate von;

    @Basic
    @Column(name = "bis")
    private LocalDate bis;

    // qualifikation_hat_student

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    public void setStudent(Student student) {
        if (this.student == student) {
            return;
        }
        this.student = student;
        this.student.addQualifikation(this);
    }

    public void removeStudent(Student student) {
        if (this.student == null) {
            return;
        }
        this.student = null;
        student.removeQualifikation(this);
    }

    // Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualifikation that = (Qualifikation) o;
        return id == that.id && Objects.equals(bezeichnung, that.bezeichnung) && Objects.equals(beschreibung, that.beschreibung) && Objects.equals(bereich, that.bereich) && Objects.equals(institution, that.institution) && Objects.equals(beschaftigungsverhaltnis, that.beschaftigungsverhaltnis) && Objects.equals(von, that.von) && Objects.equals(bis, that.bis) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bezeichnung, beschreibung, bereich, institution, beschaftigungsverhaltnis, von, bis, student);
    }
}