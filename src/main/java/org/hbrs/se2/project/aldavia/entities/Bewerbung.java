package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
//import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "bewerbungen", schema = "aldavia_new")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bewerbung {
    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "datum", nullable = false)
    private LocalDate datum = LocalDate.now();

    @PrePersist
    public void prePersist() {
        datum = LocalDate.now();
    }

    // bewerbung_hat_student

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;


    public void setStudent(Student student){
        if (this.student != null) {
            this.student.removeBewerbung(this);
        }
        this.student = student;
        if (student != null) {
            student.addBewerbung(this);
        }
    }

    // bewerbung_hat_stellenanzeige

    @ManyToOne
    @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "id", nullable = false)
    private Stellenanzeige stellenanzeige;

    public void setStellenanzeige(Stellenanzeige stellenanzeige){
        if (this.stellenanzeige != null) {
            this.stellenanzeige.removeBewerbung(this);
        }
        this.stellenanzeige = stellenanzeige;
        if (stellenanzeige != null) {
            stellenanzeige.addBewerbung(this);
        }
    }

    public void removeStudent(Student student) {
        if (this.student != null) {
            this.student.removeBewerbung(this);
        }
        this.student = null;
    }

    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewerbung bewerbung = (Bewerbung) o;
        return id == bewerbung.id && Objects.equals(datum, bewerbung.datum) && Objects.equals(student, bewerbung.student) && Objects.equals(stellenanzeige, bewerbung.stellenanzeige);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datum, student, stellenanzeige);
    }
}