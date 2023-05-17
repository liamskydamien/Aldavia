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

    @Id
    @GeneratedValue
    @Column(name= "qualifikation_id")
    private int id;

    @ManyToMany(mappedBy = "qualifikationen", fetch = FetchType.EAGER )
    private List<Student> studenten;

    @Column(name = "bezeichnung")
    private String bezeichnung;

    @Basic
    @Column(name = "bereich")
    private String bereich;

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



    public List<Student> getStudenten() {
        return studenten;
    }


}
