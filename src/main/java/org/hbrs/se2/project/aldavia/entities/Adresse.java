package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.*;

import lombok.*;

@Entity
@Table(name = "adressen", schema = "aldavia_new")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adresse {
    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "strasse", nullable = false)
    private String strasse;

    @Basic
    @Column(name = "hausnummer", nullable = false)
    private String hausnummer;

    @Basic
    @Column(name = "plz", nullable = false)
    private String plz;

    @Basic
    @Column(name = "ort", nullable = false)
    private String ort;

    @Basic
    @Column(name = "land", nullable = false)
    private String land;


    // adresse_hat_unternehmen
    @ManyToMany(mappedBy = "adressen")
    private Set<Unternehmen> unternehmen;

    public Adresse addUnternehmen(Unternehmen unternehmen) {
        if (this.unternehmen == null) {
            this.unternehmen = new HashSet<>();
        }
        if (!this.unternehmen.contains(unternehmen)) {
            this.unternehmen.add(unternehmen);
            unternehmen.addAdresse(this);
        }
        return this;
    }

    public Adresse removeUnternehmen(Unternehmen unternehmen) {
        if (this.unternehmen != null && this.unternehmen.contains(unternehmen)) {
            this.unternehmen.remove(unternehmen);
            unternehmen.removeAdresse(this);
        }
        return this;
    }

    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return id == adresse.id && Objects.equals(strasse, adresse.strasse) && Objects.equals(hausnummer, adresse.hausnummer) && Objects.equals(plz, adresse.plz) && Objects.equals(ort, adresse.ort) && Objects.equals(land, adresse.land) && Objects.equals(unternehmen, adresse.unternehmen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, strasse, hausnummer, plz, ort, land, unternehmen);
    }

    public String toString() {
        return ""+ strasse + " " + hausnummer + ", " + plz + " " + ort + ", " + land;
    }
}