package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "unternehmen", schema = "carlook")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Unternehmen {
    @Id
    @GeneratedValue
    private int unternehmenId;

    @Basic
    @Column(name = "firmenname", nullable = false)
    private String name;

    @Basic
    @Column(name = "firmenbeschreibung")
    private String beschreibung;

    @Basic
    @Column(name = "ap_vorname")
    private String ansprechpartnerVorname;

    @Basic
    @Column(name = "ap_nachname")
    private String ansprechpartnerNachname;

    @Basic
    @Column(name = "website")
    private String website;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unternehmen that = (Unternehmen) o;
        return unternehmenId == that.unternehmenId && Objects.equals(name, that.name) && Objects.equals(beschreibung, that.beschreibung) && Objects.equals(ansprechpartnerVorname, that.ansprechpartnerVorname) && Objects.equals(ansprechpartnerNachname, that.ansprechpartnerNachname) && Objects.equals(website, that.website) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unternehmenId, name, beschreibung, ansprechpartnerVorname, ansprechpartnerNachname, website, user);
    }

    // unternehmen_hat_adresse
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Adresse> adressen;
    @JoinTable(name = "unternehmen_hat_adresse", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "unternehmen_id", referencedColumnName = "unternehmenId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "adresse_id", referencedColumnName = "adresseId", nullable = false))
    public List<Adresse> getAdressen() {
        return adressen;
    }

    public void addAdresse(Adresse adresse) {
        if(adressen == null) {
            adressen = new ArrayList<>();
            adressen.add(adresse);
            adresse.addUnternehmen(this);
        } else {
            if (adressen.contains(adresse)) {
                return;
            }
            adressen.add(adresse);
            adresse.addUnternehmen(this);
        }
    }

    public void removeAdresse(Adresse adresse) {
        if (adressen == null) {
            adressen = new ArrayList<>();
        }
        else {
            if (!adressen.contains(adresse)) {
                return;
            }
            adressen.remove(adresse);
            adresse.removeUnternehmen(this);
        }
    }

    // unternehmen_erstellt_stellenanzeige
    @OneToMany(cascade = CascadeType.ALL)
    private List<Stellenanzeige> stellenanzeigen;
    @JoinTable(name = "unternehmen_erstellt_stellenanzeige", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "unternehmen_id", referencedColumnName = "unternehmenId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "stellenanzeigeId", nullable = false))
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

    public void addStellenanzeige(Stellenanzeige stellenanzeige) {
        if(stellenanzeigen == null) {
            stellenanzeigen = new ArrayList<>();
            stellenanzeigen.add(stellenanzeige);
            stellenanzeige.addUnternehmen(this);
        }
        else {
            if(stellenanzeigen.contains(stellenanzeige)) {
                return;
            }
            stellenanzeigen.add(stellenanzeige);
            stellenanzeige.addUnternehmen(this);
        }
    }


    public void removeStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeigen == null) {
            stellenanzeigen = new ArrayList<>();
        }
        else {
            if (!stellenanzeigen.contains(stellenanzeige)) {
                return;
            }
            stellenanzeigen.remove(stellenanzeige);
            stellenanzeige.removeUnternehmen(this);
        }
    }

    public void addBewertung(Bewertung bewertung) {
    }

    public void removeBewertung(Bewertung bewertung) {
    }
}
