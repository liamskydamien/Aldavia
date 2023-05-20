package org.hbrs.se2.project.aldavia.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
// import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="qualifikation" , schema = "carlook" )
@NoArgsConstructor
@Getter
@Setter
public class Qualifikation {

    @Id
    @GeneratedValue
    @Column(name= "qualifikation_id")
    private int id;

    @ManyToMany(mappedBy = "qualifikationen", fetch = FetchType.EAGER )
    private List<Student> studenten;

    @Column(name = "bezeichnung", nullable = false)
    private String bezeichnung;

    @Basic
    @Column(name = "bereich")
    private String bereich;

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;

    @Basic
    @Column(name = "beschaeftigungsart")
    private String beschaeftigungsart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualifikation qualifikation = (Qualifikation) o;
        return Objects.equals(bezeichnung, qualifikation.bezeichnung)
                && Objects.equals(bereich, qualifikation.bereich)
                && Objects.equals(beschreibung, qualifikation.beschreibung)
                && Objects.equals(beschaeftigungsart, qualifikation.beschaeftigungsart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, bereich, beschreibung, beschaeftigungsart);
    }







}
