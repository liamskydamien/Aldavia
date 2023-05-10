package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="bewertung" , schema = "aldavia" )
public class Bewertung  {
    private int id;
    private int note;
    private Student student;
    private Unternehmen unternehmen;
    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "note")
    public int getNote() {return note;}
    public void setNote(int note) {this.note = note;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewertung bewertung = (Bewertung) o;
        return id == bewertung.id &&
                Objects.equals(note, bewertung.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note);
    }

    @ManyToOne
    @JoinTable(name = "student_bewertet", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false))
    public Student getStudent() { return student; }
    public void setStudent(Student student) {this.student = student;}

    @ManyToOne
    @JoinTable(name = "unternehmen_hat_bewertung", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false))
    public Unternehmen getUnternehmen() { return unternehmen; }
    public void setUnternehmen(Unternehmen unternehmen) {this.unternehmen = unternehmen;}
}

