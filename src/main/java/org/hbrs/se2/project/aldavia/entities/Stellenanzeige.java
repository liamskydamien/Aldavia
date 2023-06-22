package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "stellenanzeigen", schema = "aldavia_new")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Stellenanzeige {

    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "bezeichnung", nullable = false)
    private String bezeichnung;

    @Basic
    @Column(name = "beschreibung", nullable = false)
    private String beschreibung;

    @Basic
    @Column(name = "beschaeftigungsverhaeltnis", nullable = false)
    private String beschaeftigungsverhaeltnis;

    @Basic
    @Column(name = "start", nullable = false)
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

    // stellenanzeige_taeitgkeitsfeld

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Taetigkeitsfeld> taetigkeitsfelder;

    @JoinTable(
            name = "stellenanzeige_qualifikation",
            joinColumns = @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "taetigkeitsfeld_id", referencedColumnName = "bezeichnung", nullable = false),
            schema = "test_schema"
    )
    public List<Taetigkeitsfeld> getTaetigkeitsbereiche() {
        return taetigkeitsfelder;
    }

    public void addTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfelder == null) {
            taetigkeitsfelder = new ArrayList<>();
        }
        if (!taetigkeitsfelder.contains(taetigkeitsfeld)){
            taetigkeitsfelder.add(taetigkeitsfeld);
            taetigkeitsfeld.addStellenanzeige(this);
        }
    }

    public void removeTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfelder == null) {
            return;
        }
        taetigkeitsfelder.remove(taetigkeitsfeld);
        taetigkeitsfeld.removeStellenanzeige(this);
    }

    // stellenanzeige_hat_bewerbungen
    @OneToMany(mappedBy = "stellenanzeige", cascade = CascadeType.ALL)
    private List<Bewerbung> bewerbungen;

    @JoinTable(
            name = "stellenanzeige_bewerbung",
            joinColumns = @JoinColumn(name = "stellenanzeige_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewerbung_id", referencedColumnName = "id", nullable = false),
            schema = "test_schema"
    )
    public List<Bewerbung> getBewerbungen() {
        return bewerbungen;
    }

    public void addBewerbung(Bewerbung bewerbung){
        if (bewerbungen == null) {
            bewerbungen = new ArrayList<>();
        }
        if (!bewerbungen.contains(bewerbung)) {
            bewerbungen.add(bewerbung);
            bewerbung.setStellenanzeige(this);
        }
    }

    public void removeBewerbung(Bewerbung bewerbung) {
        if (bewerbungen == null) {
            return;
        }
        bewerbungen.remove(bewerbung);
        bewerbung.setStellenanzeige(null);
    }

    // stellenanzeige_hat_unternehmen
    @ManyToOne
    @JoinColumn(name = "unternehmen_id", referencedColumnName = "id", nullable = false)
    private Unternehmen unternehmen_stellenanzeigen;

    public void setUnternehmen(Unternehmen unternehmen) {
        if (this.unternehmen_stellenanzeigen != null && !(this.unternehmen_stellenanzeigen.equals(unternehmen))) {
            this.unternehmen_stellenanzeigen.removeStellenanzeige(this);
        }
        this.unternehmen_stellenanzeigen = unternehmen;
        if (unternehmen != null) {
            unternehmen.addStellenanzeige(this);
        }
    }


    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stellenanzeige that = (Stellenanzeige) o;
        return id == that.id && Objects.equals(bezeichnung, that.bezeichnung) && Objects.equals(beschreibung, that.beschreibung) && Objects.equals(beschaeftigungsverhaeltnis, that.beschaeftigungsverhaeltnis) && Objects.equals(start, that.start) && Objects.equals(ende, that.ende) && Objects.equals(erstellungsdatum, that.erstellungsdatum) && Objects.equals(bezahlung, that.bezahlung) && Objects.equals(beschaeftigungsumfang, that.beschaeftigungsumfang) && Objects.equals(taetigkeitsfelder, that.taetigkeitsfelder) && Objects.equals(bewerbungen, that.bewerbungen) && Objects.equals(unternehmen_stellenanzeigen, that.unternehmen_stellenanzeigen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bezeichnung, beschreibung, beschaeftigungsverhaeltnis, start, ende, erstellungsdatum, bezahlung, beschaeftigungsumfang, unternehmen_stellenanzeigen);
    }
}