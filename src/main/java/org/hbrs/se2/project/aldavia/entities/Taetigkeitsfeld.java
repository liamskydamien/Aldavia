package org.hbrs.se2.project.aldavia.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
// import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="taetigkeitsfeld" , schema = "carlook" )
@NoArgsConstructor
@Setter
@Getter
public class Taetigkeitsfeld {
    private String bezeichnung;
    private List<Stellenanzeige> stellenanzeigen;
    private List<Student> studenten;
    @Id
    @Column(name = "bezeichnung", nullable = false, unique = true)
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taetigkeitsfeld taetigkeitsfeld = (Taetigkeitsfeld) o;
        return Objects.equals(bezeichnung, taetigkeitsfeld.bezeichnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }


    @ManyToMany(mappedBy = "taetigkeitsfelder", fetch = FetchType.LAZY)
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

    public void addStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeige == null) {
            stellenanzeigen = new ArrayList<>();
        }
        else {
            if (this.stellenanzeigen.contains(stellenanzeige))
                return;
            this.stellenanzeigen.add(stellenanzeige);
            stellenanzeige.addTaetigkeitsfeld(this);
        }
    }

    public void removeStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeige == null) {
            stellenanzeigen = new ArrayList<>();
        }
        else {
            if (!this.stellenanzeigen.contains(stellenanzeige))
                return;
            this.stellenanzeigen.remove(stellenanzeige);
            stellenanzeige.removeTaetigkeitsfeld(this);
        }
    }

    @ManyToMany(mappedBy = "taetigkeitsfelder", fetch = FetchType.LAZY)
    public List<Student> getStudenten() {
        return studenten;
    }

    public void addStudent(Student student) {
        if (student == null) {
            studenten = new ArrayList<>();
        }
        else {
            if (this.studenten.contains(student)) {
                return;
            }
            this.studenten.add(student);
            student.addTaetigkeitsfeld(this);
        }
    }

    public void removeStudent(Student student) {
        if (student == null) {
            studenten = new ArrayList<>();
        }
        else {
            if (!this.studenten.contains(student)) {
                return;
            }
            this.studenten.remove(student);
            student.removeTaetigkeitsfeld(this);
        }
    }

}
