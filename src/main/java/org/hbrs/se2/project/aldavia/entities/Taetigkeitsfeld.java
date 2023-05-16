package org.hbrs.se2.project.aldavia.entities;

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
public class Taetigkeitsfeld {
    private String bezeichnung;
    private List<Stellenanzeige> stellenanzeigen;
    @Id
    @Column(name = "bezeichnung")
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


    @ManyToMany(mappedBy = "taetigkeitsfelder", fetch = FetchType.EAGER )
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

}
