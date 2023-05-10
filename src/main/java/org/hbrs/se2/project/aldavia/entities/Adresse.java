package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
// import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="adresse" , schema = "aldavia" )
public class Adresse {
    private int id;
    private String land;
    private String ort;
    private String strasse;
    private String postleitzahl;
    private String hausnummer;
    private List<Unternehmen> unternehmen;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "land")
    public String getLand() {return land;}
    public void setLand (String land) {this.land = land;}

    @Basic
    @Column(name = "ort")
    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Basic
    @Column(name = "postleitzahl")
    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    @Basic
    @Column(name = "strasse")
    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    @Basic
    @Column(name = "hausnummer")
    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return id == adresse.id &&
                Objects.equals(land, adresse.land) &&
                Objects.equals(ort, adresse.ort) &&
                Objects.equals(postleitzahl, adresse.postleitzahl) &&
                Objects.equals(strasse, adresse.strasse) &&
                Objects.equals(hausnummer, adresse.hausnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, land, ort, postleitzahl, strasse, hausnummer);
    }

    @ManyToMany(mappedBy = "adressen", fetch = FetchType.EAGER)
    public List<Unternehmen> getUnternehmen() {return unternehmen;}
    public void setUnternehmen(List<Unternehmen> unternehmen) {this.unternehmen = unternehmen;}

}
