package org.hbrs.se2.project.aldavia.entities;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
// import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="qualifikation" , schema = "carlook" )
@NoArgsConstructor
@Setter
public class Qualifikation {
    private String bezeichnung;
    private String bereich;
    private List<Student> studenten;
    @Id
    @Column(name = "bezeichnung")
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Basic
    @Column(name = "bereich")
    public String getBereich() { return bereich; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualifikation qualifikation = (Qualifikation) o;
        return Objects.equals(bezeichnung, qualifikation.bezeichnung) &&
                Objects.equals(bereich, qualifikation.bereich);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }


    @ManyToMany(mappedBy = "qualifikationen", fetch = FetchType.EAGER )
    public List<Student> getStudenten() {
        return studenten;
    }


}
