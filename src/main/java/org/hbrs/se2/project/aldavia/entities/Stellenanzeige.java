package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "stellenanzeige", schema = "carlook")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Stellenanzeige {
    @Id
    @GeneratedValue
    @Column(name = "stellenanzeigeId", nullable = false)
    private int stellenanzeigeId;

    @Basic
    @Column(name = "bezeichnung", nullable = false)
    private String bezeichnung;

    @Basic
    @Column(name = "start")
    private LocalDate start;

    @Basic
    @Column(name = "ende")
    private LocalDate ende;

    @Basic
    @Column(name = "erstellungsdatum")
    private LocalDate erstellungsdatum;

    @Basic
    @Column(name = "bezahlung")
    private String bezahlung;

    @Basic
    @Column(name = "beschaeftigungsumfang")
    private String beschaeftigungsumfang;

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stellenanzeige stellenanzeige = (Stellenanzeige) o;
        return stellenanzeigeId == stellenanzeige.stellenanzeigeId &&
                Objects.equals(bezeichnung, stellenanzeige.bezeichnung) &&
                Objects.equals(beschreibung, stellenanzeige.beschreibung) &&
                Objects.equals(start, stellenanzeige.start) &&
                Objects.equals(ende, stellenanzeige.ende) &&
                Objects.equals(erstellungsdatum, stellenanzeige.erstellungsdatum) &&
                Objects.equals(bezahlung, stellenanzeige.bezahlung) &&
                Objects.equals(beschaeftigungsumfang, stellenanzeige.beschaeftigungsumfang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stellenanzeigeId, bezeichnung, beschreibung, start, ende, erstellungsdatum, bezahlung, beschaeftigungsumfang);
    }

    // unternehmen_erstellt_stellenanzeige
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ersteller_id")
    private Unternehmen ersteller;

    public void setErsteller(Unternehmen ersteller) {
        this.ersteller = ersteller;
        if (ersteller!= null && !ersteller.getStellenanzeigen().contains(this)) {
            ersteller.getStellenanzeigen().add(this);
        }
    }



    // stellenanzeige_hat_taetigkeitsfeld
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "stellenanzeige_hat_taetigkeitsfeld", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "stellenanzeigeId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "taetigkeitsfeld", referencedColumnName = "bezeichnung", nullable = false))
    private List<Taetigkeitsfeld> taetigkeitsfelder;
    
    public void addTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfelder == null) {
            taetigkeitsfelder = new ArrayList<>();
        }
        if (taetigkeitsfelder.contains(taetigkeitsfeld)){
            return;
        }
        taetigkeitsfelder.add(taetigkeitsfeld);
        taetigkeitsfeld.addStellenanzeige(this);
    }



    // stellenanzeige_hat_bewerbung
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bewerbung> bewerbungen;
    @JoinTable(name = "stellenanzeige_hat_bewerbung", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "stellenanzeigeId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewerbung_id", referencedColumnName = "stellenanzeigeId", nullable = false))
    public List<Bewerbung> getBewerbung() {
        return bewerbungen;
    }

    public void addBewerbung(Bewerbung bewerbung) {
        if (bewerbungen == null) {
            bewerbungen = new ArrayList<>();
        } else {
            if (bewerbungen.contains(bewerbung)) {
                return;
            }
        }
        bewerbungen.add(bewerbung);
        bewerbung.addStellenanzeige(this);
    }

    public void removeBewerbung(Bewerbung bewerbung) {
        if (bewerbungen == null) {
            bewerbungen = new ArrayList<>();
        } else {
            if (!bewerbungen.contains(bewerbung)) {
                return;
            }
            bewerbungen.remove(bewerbung);
            bewerbung.removeStellenanzeige(this);
        }
    }


    // student_favorisiert_stellenanzeige
    @ManyToMany(mappedBy = "stellenanzeigenFavourisiert", cascade = CascadeType.ALL)
    private List<Student> studenten;
    public List<Student> getStudenten() {
        return studenten;
    }
    
    public void addStudent(Student student) {
        if (studenten == null) {
            studenten = new ArrayList<>();
        }
        if (studenten.contains(student)){
            return;
        }
        studenten.add(student);
        student.addStellenanzeige(this);
    }
    
    public void removeStudent(Student student) {
        if (studenten == null) {
            studenten = new ArrayList<>();
        }
        if (!studenten.contains(student)){
            return;
        }
        studenten.remove(student);
        student.removeStellenanzeige(this);
    }
    
    public void setStudenten(List<Student> studenten) {
        this.studenten = studenten;
    }

    public void removeUnternehmen(Unternehmen unternehmen) {
        if (ersteller == null) {
            return;
        }
        if (!ersteller.equals(unternehmen)) {
            return;
        }
        ersteller = null;
        unternehmen.removeStellenanzeige(this);
    }

    public void addUnternehmen(Unternehmen unternehmen) {
        if (ersteller == null) {
            ersteller = unternehmen;
            unternehmen.addStellenanzeige(this);
        }
    }

    public void removeTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfelder == null) {
            taetigkeitsfelder = new ArrayList<>();
        } else {
            if (!taetigkeitsfelder.contains(taetigkeitsfeld)) {
                return;
            }
            taetigkeitsfelder.remove(taetigkeitsfeld);
            taetigkeitsfeld.removeStellenanzeige(this);
        }
    }
}
