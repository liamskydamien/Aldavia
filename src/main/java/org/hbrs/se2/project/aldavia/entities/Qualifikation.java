package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="qualifikation" , schema = "aldavia" )
public class Qualifikation  {
    private String bezeichnung;
    private String bereich;
    private List<Student> studenten;
    @Id
    @Column(name = "bezeichnung")
    public String getBezeichnung() {return bezeichnung;}
    public void setBezeichnung(String bezeichnung) {this.bezeichnung = bezeichnung;}

    @Basic
    @Column(name = "bereich")
    public String getBereich() {
        return bereich;
    }
    public void setBereich(String bereich) {this.bereich = bereich;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualifikation qualifikation = (Qualifikation) o;
        return bezeichnung == qualifikation.bezeichnung &&
                Objects.equals(bereich, qualifikation.bereich);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, bereich);
    }

    @ManyToMany(mappedBy = "qualifikationen", fetch = FetchType.EAGER)
    public List<Student> getStudenten() {
        return studenten;
    }

    public void setStudenten(List<Student> studenten) {
        this.studenten = studenten;
    }
}

