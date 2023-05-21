package org.hbrs.se2.project.aldavia.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
//import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="bewertung" , schema = "carlook" )
@Getter
@Setter
@EqualsAndHashCode
public class Bewertung  {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int bewertungId;

    @Basic
    @Column(name = "note", nullable = false)
    private int note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewertung bewertung = (Bewertung) o;
        return bewertungId == bewertung.bewertungId &&
                Objects.equals(note, bewertung.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bewertungId, note);
    }

    @ManyToOne
    private Student student;
    public Student getStudent() { return student; }
    public void setStudent(Student student) {this.student = student;}

    @ManyToOne
    private Unternehmen unternehmen;
    public Unternehmen getUnternehmen() { return unternehmen; }
    public void setUnternehmen(Unternehmen unternehmen) {this.unternehmen = unternehmen;}

    public void addStudent(Student student) {
        if (student == null)
            return;
        if (this.student == null)
            this.student = student;
        if (this.student.equals(student))
            return;
        this.student = student;
        student.addBewertung(this);
    }

    public void addUnternehmen(Unternehmen unternehmen) {
        if (unternehmen == null)
            return;
        if (this.unternehmen == null)
            this.unternehmen = unternehmen;
        if (this.unternehmen.equals(unternehmen))
            return;
        this.unternehmen = unternehmen;
        unternehmen.addBewertung(this);
    }

    public void removeStudent(Student student) {
        if (student == null)
            return;
        if (!student.equals(this.student))
            return;
        this.student = null;
        student.removeBewertung(this);
    }

    public void removeUnternehmen(Unternehmen unternehmen) {
        if (unternehmen == null)
            return;
        if (!unternehmen.equals(this.unternehmen))
            return;
        this.unternehmen = null;
        unternehmen.removeBewertung(this);
    }
}
