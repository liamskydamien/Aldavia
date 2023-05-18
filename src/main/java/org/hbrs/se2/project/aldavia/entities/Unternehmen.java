package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "firmenname")
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


    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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
    @ManyToMany
    private List<Adresse> adressen;
    @JoinTable(name = "unternehmen_hat_adresse", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "unternehmen_id", referencedColumnName = "unternehmenId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "adresse_id", referencedColumnName = "adresseId", nullable = false))
    public List<Adresse> getAdressen() {
        return adressen;
    }

    // unternehmen_erstellt_stellenanzeige

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ersteller")
    private List<Stellenanzeige> stellenanzeigen;


    @JoinTable(name = "unternehmen_erstellt_stellenanzeige", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "unternehmen_id", referencedColumnName = "unternehmenId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "stellenanzeigeId", nullable = false))
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

}
