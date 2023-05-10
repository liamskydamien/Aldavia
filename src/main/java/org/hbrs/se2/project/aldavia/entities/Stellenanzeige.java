package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
// import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="stellenanzeige" , schema = "aldavia" )
public class Stellenanzeige {
    private int id;
    private String bezeichnung;
    private LocalDate start;
    private LocalDate ende;
    private LocalDate erstellungsdatum;
    private double bezahlung;
    private String beschaeftigungsumfang;
    private String beschreibung;
    private Unternehmen unternehmen;
    private List<Student> studenten;
    private List<Taetigkeitsfeld> taetigkeitsfelder;
    private List<Bewerbung> bewerbungen;

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
    @Column(name = "bezeichnung")
    public String getBezeichnung() {return bezeichnung;}
    public void setBezeichnung (String bezeichnung) {this.bezeichnung = bezeichnung;}

    @Basic
    @Column(name = "start")
    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    @Basic
    @Column(name = "ende")
    public LocalDate getEnde() {
        return ende;
    }

    public void setEnde(LocalDate ende) {
        this.ende = ende;
    }

    @Basic
    @Column(name = "erstellungsdatum")
    public LocalDate getErstellungsdatum() {
        return erstellungsdatum;
    }

    public void setErstellungsdatum(LocalDate erstellungsdatum) {
        this.erstellungsdatum = erstellungsdatum;
    }

    @Basic
    @Column(name = "bezahlung")
    public double getBezahlung() {
        return bezahlung;
    }
    public void setBezahlung(double bezahlung) {
        this.bezahlung = bezahlung;
    }

    @Basic
    @Column(name = "beschaeftigungsumfang")
    public String getBeschaeftigungsumfang() {
        return beschaeftigungsumfang;
    }
    public void setBeschaeftigungsumfang(String beschaeftigungsumfang) {this.beschaeftigungsumfang = beschaeftigungsumfang;}

    @Basic
    @Column(name = "beschreibung")
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {this.beschreibung = beschreibung;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stellenanzeige stellenanzeige = (Stellenanzeige) o;
        return id == stellenanzeige.id &&
                Objects.equals(bezeichnung, stellenanzeige.bezeichnung) &&
                Objects.equals(start, stellenanzeige.start) &&
                Objects.equals(ende, stellenanzeige.ende) &&
                Objects.equals(erstellungsdatum, stellenanzeige.erstellungsdatum) &&
                Objects.equals(bezahlung, stellenanzeige.bezahlung) &&
                Objects.equals(beschaeftigungsumfang, stellenanzeige.beschaeftigungsumfang) &&
                Objects.equals(beschreibung, stellenanzeige.beschreibung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bezeichnung, start, ende, erstellungsdatum, bezahlung, beschaeftigungsumfang, beschreibung);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "unternehmen_erstellt_stellenanzeige", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false))
    public Unternehmen getUnternehmen() { return unternehmen; }
    public void setUnternehmen(Unternehmen unternehmen) {this.unternehmen = unternehmen;}

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "student_hat_qualifikation", catalog = "demouser",
//            schema = "aldavia",
//            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
//    public List<Qualifikation> getQualifikationen() {
//        return qualifikationen;
//    }
//    public void setQualifikationen(List<Qualifikation> qualifikationen) {this.qualifikationen = qualifikationen;}
    @ManyToMany(mappedBy = "studenten")
    public List<Student> getStudenten() {return studenten;}
    public void setStudenten(List<Student> studenten) {this.studenten = studenten;}

    @ManyToMany(mappedBy = "stellenanzeigen")
    public List<Taetigkeitsfeld> getTaetigkeitsfelder() {
        return taetigkeitsfelder;
    }
    public void setTaetigkeitsfelder(List<Taetigkeitsfeld> taetigkeitsfelder) {this.taetigkeitsfelder = taetigkeitsfelder;}

    @OneToMany(mappedBy = "stellenanzeige", cascade = CascadeType.ALL)
    private List<Bewerbung> getBewerbungen() {return bewerbungen;}
    private void setBewerbungen(List<Bewerbung> bewerbungen) {this.bewerbungen = bewerbungen;}
}
