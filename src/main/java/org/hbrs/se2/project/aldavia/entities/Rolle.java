package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="rolle" , schema = "aldavia" )
public class Rolle {
    private String bezeichnung;
    private List<Benutzer> benutzer;

    @Id
    @Column(name = "bezeichnung")
    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolle rolle = (Rolle) o;
        return Objects.equals(bezeichnung, rolle.bezeichnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }

    @ManyToMany(mappedBy = "rollen", fetch = FetchType.EAGER )
    public List<Benutzer> getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }
}
