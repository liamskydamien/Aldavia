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
@Table( name ="adresse" , schema = "carlook" )
@NoArgsConstructor
@Getter
@Setter
public class Adresse {
    @Id
    @GeneratedValue
    private int adresseId;

    @Basic
    @Column(name = "land")
    private String land;

    @Basic
    @Column(name = "ort")
    private String ort;
    @Basic
    @Column(name = "postleitzahl")
    private String postleitzahl;

    @Basic
    @Column(name = "strasse")
    private String strasse;

    @Basic
    @Column(name = "hausnummer")
    private String hausnummer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return adresseId == adresse.adresseId
                && Objects.equals(land, adresse.land)
                && Objects.equals(ort, adresse.ort)
                && Objects.equals(postleitzahl, adresse.postleitzahl)
                && Objects.equals(strasse, adresse.strasse)
                && Objects.equals(hausnummer, adresse.hausnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adresseId, land, ort, postleitzahl, strasse, hausnummer);
    }

    @ManyToMany(mappedBy = "adressen", fetch = FetchType.EAGER )
    private List<Unternehmen> unternehmen;
    public List<Unternehmen> getUnternehmen() {
        return unternehmen;
    }

    public void addUnternehmen(Unternehmen unternehmen) {
        if (this.unternehmen == null)
            this.unternehmen = new ArrayList<>();
        if (!this.unternehmen.contains(unternehmen))
            return;
        this.unternehmen.add(unternehmen);
        unternehmen.addAdresse(this);
    }

    public void removeUnternehmen(Unternehmen unternehmen) {
        if (this.unternehmen == null)
            return;
        if (!this.unternehmen.contains(unternehmen))
            return;
        this.unternehmen.remove(unternehmen);
        unternehmen.removeAdresse(this);
    }
}
