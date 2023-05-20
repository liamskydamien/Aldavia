package org.hbrs.se2.project.aldavia.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
// import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="taetigkeitsfeld" , schema = "carlook" )
@NoArgsConstructor
@Setter
@Getter
public class Taetigkeitsfeld {


    @Id
    @Column(name = "bezeichnung", nullable = false, unique = true)
    private String bezeichnung;

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


    @ManyToMany(mappedBy = "taetigkeitsfelder", cascade = CascadeType.ALL)
    private List<Stellenanzeige> stellenanzeigen;

    @ManyToMany(mappedBy = " taetigkeitsfelder")
    private List<Student> studenten;



}
