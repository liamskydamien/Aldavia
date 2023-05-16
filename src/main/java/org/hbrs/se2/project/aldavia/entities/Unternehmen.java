package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
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
}
