package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="student" , schema = "aldavia" )
public class Student extends Benutzer {
    private int matrikelnummer;
    private LocalDate studienstart;
    private String lebenslauf;
    private String beschreibung;
    private LocalDate geburtsdatum;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String studiengang;
    private List<Kenntnis> kenntnisse;
    private List<Qualifikation> qualifikationen;
    private List<Stellenanzeige> stellenanzeigen;
    private List<Taetigkeitsfeld> taetigkeitsfelder;
    private List<Bewerbung> bewerbungen;
    private List<Bewertung> bewertungen;

    @Basic
    @Column(name = "matrikelnummer")
    public int getMatrikelnummer() {return matrikelnummer;}
    public void setMatrikelnummer (int matrikelnummer) {this.matrikelnummer = matrikelnummer;}

    @Basic
    @Column(name = "studienstart")
    public LocalDate getStudienstart() {
        return studienstart;
    }

    public void setStudienstart(LocalDate studienstart) {
        this.studienstart = studienstart;
    }

    @Basic
    @Column(name = "lebenslauf")
    public String getLebenslauf() {
        return lebenslauf;
    }
    public void setLebenslauf(String lebenslauf) {
        this.lebenslauf = lebenslauf;
    }

    @Basic
    @Column(name = "beschreibung")
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Basic
    @Column(name = "geburtsdatum")
    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    @Basic
    @Column(name = "vorname")
    public String getVorname() {return vorname;}

    public void setVorname(String vorname) {this.vorname = vorname;}

    @Basic
    @Column(name = "nachname")
    public String getNachname() {return nachname;}

    public void setNachname(String nachname) {this.nachname = nachname;}

    @Basic
    @Column(name = "telefonnummer")
    public String getTelefonnummer() {
        return telefonnummer;
    }
    public void setTelefonnummer(String telefonnummer) {this.telefonnummer = telefonnummer;}

    @Basic
    @Column(name = "studiengang")
    public String getStudiengang() {
        return studiengang;
    }
    public void setStudiengang(String studiengang) {this.studiengang = studiengang;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(matrikelnummer, student.matrikelnummer) &&
                Objects.equals(studienstart, student.studienstart) &&
                Objects.equals(lebenslauf, student.lebenslauf) &&
                Objects.equals(beschreibung, student.beschreibung) &&
                Objects.equals(geburtsdatum, student.geburtsdatum) &&
                Objects.equals(vorname, student.vorname) &&
                Objects.equals(nachname, student.nachname) &&
                Objects.equals(telefonnummer, student.telefonnummer) &&
                Objects.equals(studiengang, student.studiengang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrikelnummer, studienstart, lebenslauf, beschreibung, geburtsdatum, vorname, nachname, telefonnummer, studiengang);
    }

    @ManyToMany
    @JoinTable(name = "student_hat_kenntnis", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
    public List<Kenntnis> getKenntnisse() {
        return kenntnisse;
    }
    public void setKenntnisse(List<Kenntnis> kenntnisse) {this.kenntnisse = kenntnisse;}

    @ManyToMany
    @JoinTable(name = "student_hat_qualifikation", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
    public List<Qualifikation> getQualifikationen() {
        return qualifikationen;
    }
    public void setQualifikationen(List<Qualifikation> qualifikationen) {this.qualifikationen = qualifikationen;}

//    @ManyToMany
//    @JoinTable(name = "student_bewertet_unternehmen", catalog = "demouser",
//            schema = "aldavia",
//            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
//    public List<Qualifikation> getQualifikationen() {
//        return qualifikationen;
//    }
//    public void setQualifikationen(List<Qualifikation> qualifikationen) {this.qualifikationen = qualifikationen;}

    @ManyToMany
    @JoinTable(name = "student_favorisiert_stellenanzeige", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id", nullable = false))
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }
    public void setStellenanzeigen(List<Stellenanzeige> stellenanzeigen) {this.stellenanzeigen = stellenanzeigen;}

    @ManyToMany(mappedBy = "studenten")
    public List<Taetigkeitsfeld> getTaetigkeitsfelder() {
        return taetigkeitsfelder;
    }
    public void setTaetigkeitsfelder(List<Taetigkeitsfeld> taetigkeitsfelder) {this.taetigkeitsfelder = taetigkeitsfelder;}

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Bewerbung> getBewerbungen() {return bewerbungen;}
    private void setBewerbungen(List<Bewerbung> bewerbungen) {this.bewerbungen = bewerbungen;}

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Bewertung> getBewertungen() {return bewertungen;}
    private void setBewertungen(List<Bewertung> bewertungen) {this.bewertungen = bewertungen;}

}
