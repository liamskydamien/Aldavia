package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table( name ="bewerbung" , schema = "aldavia" )
public class Bewerbung  {
    private int id;
    private LocalDate datum;
    private Student student;
    private Stellenanzeige stellenanzeige;
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
    @Column(name = "datum")
    public LocalDate getDatum() {return datum;}
    public void setDatum(LocalDate datum) {this.datum = datum;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewerbung bewerbung = (Bewerbung) o;
        return id == bewerbung.id &&
                Objects.equals(datum, bewerbung.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datum);
    }

    @ManyToOne
    @JoinTable(name = "student_bewirbt_sich", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false))
    public Student getStudent() { return student; }
    public void setStudent(Student student) {this.student = student;}

    @ManyToOne
    @JoinTable(name = "stellenanzeige_hat_bewerbung", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false))
    public Stellenanzeige getStellenanzeige() { return stellenanzeige; }
    public void setStellenanzeige(Stellenanzeige stellenanzeige) {this.stellenanzeige = stellenanzeige;}
}

