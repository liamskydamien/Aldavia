package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="kenntnis" , schema = "aldavia" )
public class Kenntnis  {
    private String bezeichnung;
    private List<Student> studenten;
    @Id
    @Column(name = "bezeichnung")
    public String getBezeichnung() {return bezeichnung;}
    public void setBezeichnung(String bezeichnung) {this.bezeichnung = bezeichnung;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kenntnis kenntnis = (Kenntnis) o;
        return bezeichnung == kenntnis.bezeichnung;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }

    @ManyToMany(mappedBy = "kenntnisse", fetch = FetchType.EAGER)
    public List<Student> getStudenten() {return studenten;}

    public void setStudenten(List<Student> studenten) {
        this.studenten = studenten;
    }
}

