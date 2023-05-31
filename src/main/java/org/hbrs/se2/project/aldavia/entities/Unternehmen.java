package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "unternehmen", schema = "aldavia_new")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Unternehmen {
    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "firmenname", nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name ="beschreibung")
    private String beschreibung;

    @Basic
    @Column(name = "ap_vorname")
    private String ap_vorname;

    @Basic
    @Column(name = "ap_nachname")
    private String ap_nachname;

    @Basic
    @Column(name = "webseite")
    private String webseite;

    // unternehmen_hat_user
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // unternehmen_erstellt_stellenanzeige

    @OneToMany(mappedBy = "unternehmen_stellenanzeigen", cascade = CascadeType.ALL)
    private List<Stellenanzeige> stellenanzeigen;


    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

    public void addStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeigen == null){
            stellenanzeigen = new ArrayList<>();
        }
        if(!stellenanzeigen.contains(stellenanzeige)) {
            stellenanzeigen.add(stellenanzeige);
            stellenanzeige.setUnternehmen(this);
        }
    }

    public void removeStellenanzeige(Stellenanzeige stellenanzeige) {
        if (stellenanzeigen != null && stellenanzeigen.contains(stellenanzeige)) {
            stellenanzeigen.remove(stellenanzeige);
            stellenanzeige.setUnternehmen(null);
        }
    }

    // unternehmen_hat_adresse

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Adresse> adressen;

    @JoinTable(
            name = "unternehmen_hat_adresse",
            joinColumns = @JoinColumn(name = "unternehmen_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "adresse_id", referencedColumnName = "id", nullable = false),
            schema = "test_schema"
    )
    public Set<Adresse> getAdressen() {
        return adressen;
    }

    public void addAdresse(Adresse adresse) {
        if (adressen == null){
            adressen = new HashSet<Adresse>();
        }
        if(!adressen.contains(adresse)) {
            adressen.add(adresse);
            adresse.addUnternehmen(this);
        }
    }

    public void removeAdresse(Adresse adresse) {
        if (adressen != null && adressen.contains(adresse)) {
            adressen.remove(adresse);
            adresse.removeUnternehmen(this);
        }
    }

    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unternehmen that = (Unternehmen) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(beschreibung, that.beschreibung) && Objects.equals(ap_vorname, that.ap_vorname) && Objects.equals(ap_nachname, that.ap_nachname) && Objects.equals(webseite, that.webseite) && Objects.equals(user, that.user) && Objects.equals(stellenanzeigen, that.stellenanzeigen) && Objects.equals(adressen, that.adressen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, beschreibung, ap_vorname, ap_nachname, webseite, user, stellenanzeigen, adressen);
    }
}
