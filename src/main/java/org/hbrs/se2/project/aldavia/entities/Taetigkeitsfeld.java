package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="taetigkeitsfeld" , schema = "aldavia" )
public class Taetigkeitsfeld  {
    private String bezeichnung;
    private List<Student> studenten;
    private List<Stellenanzeige> stellenanzeigen;
    @Id
    @Column(name = "bezeichnung")
    public String getBezeichnung() {return bezeichnung;}
    public void setBezeichnung(String bezeichnung) {this.bezeichnung = bezeichnung;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taetigkeitsfeld taetigkeitsfeld = (Taetigkeitsfeld) o;
        return bezeichnung == taetigkeitsfeld.bezeichnung;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_interessiert_sich_fuer_taetigkeitsfeld", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false))
    public List<Student> getStudenten() {
        return studenten;
    }
    public void setStudenten(List<Student> studenten) {
        this.studenten = studenten;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "stellenanzeige_hat_taetigkeitsfeld", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false))
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }
    public void setStellenanzeigen(List<Stellenanzeige> stellenanzeigen) {
        this.stellenanzeigen = stellenanzeigen;
    }
}

