package org.hbrs.se2.project.aldavia.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table( name ="bewerbung" , schema = "carlook" )
@NoArgsConstructor
@Getter
@Setter
public class Bewerbung  {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private int bewerbungId;

    @Basic
    @Column(name = "datum", nullable = false)
    private LocalDate datum = LocalDate.now();

    @PrePersist
    public void prePersist() {
        datum = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewerbung bewerbung = (Bewerbung) o;
        return bewerbungId == bewerbung.bewerbungId &&
                Objects.equals(datum, bewerbung.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bewerbungId, datum);
    }

    @ManyToOne
    private Student student;
    public Student getStudent() { return student; }
    public void setStudent(Student student) {
        if (student == null) {
            throw new NullPointerException("Student cannot be null");
        }
        this.student = student;
    }

    @ManyToOne
    private Stellenanzeige stellenanzeige;
    public Stellenanzeige getStellenanzeige() { return stellenanzeige; }
    public void setStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeige == null) {
            throw new NullPointerException("Stellenanzeige cannot be null");
        }
    this.stellenanzeige = stellenanzeige;
    }
}