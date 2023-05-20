package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "student", schema = "carlook")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student{
    @Basic
    @Column(name = "vorname", nullable = false)
    private String vorname;

    @Basic
    @Column(name = "nachname", nullable = false)
    private String nachname;

    @Basic
    @Column(name = "matrikelnummer")
    private String matrikelNummer;

    @Basic
    @Column(name = "studiengang")
    private String studiengang;

    @Basic
    @Column(name = "studienbeginn")
    private LocalDate studienbeginn;

    @Basic
    @Column(name = "geburtsdatum")
    private LocalDate geburtsdatum;

    @Basic
    @Column(name = "lebenslauf")
    private String lebenslauf;

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private User user;

    @OneToMany
    private List<Bewertung> bewertungen;

    @OneToMany
    private List<Bewerbung> bewerbungen;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Kenntnis> kenntnisse;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Qualifikation> qualifikationen;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Taetigkeitsfeld> taetigkeitsfelder;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Stellenanzeige> stellenanzeigen;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Sprache> sprachen;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int studentId;

    public Student(String vorname, String nachname, String matrikelNummer, String studiengang, LocalDate studienbeginn, LocalDate geburtsdatum, String lebenslauf) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.matrikelNummer = matrikelNummer;
        this.studiengang = studiengang;
        this.studienbeginn = studienbeginn;
        this.geburtsdatum = geburtsdatum;
        this.lebenslauf = lebenslauf;

    }

    // student_hat_qualifikation
    @JoinTable(name = "student_hat_qualifikation", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "qualifikation", referencedColumnName = "qualifikation_id", nullable = false))
    public List<Qualifikation> getQualifikationen() {
        return qualifikationen;
    }

    public void addQualifikation(Qualifikation qualifikation) {
        if (this.qualifikationen == null){
            this.qualifikationen = new ArrayList<>();
        }
        if (this.qualifikationen.contains(qualifikation)){
            return;
        }
        this.qualifikationen.add(qualifikation);
    }

    // student_hat_kenntnis
    @JoinTable(name = "student_hat_kenntnis", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "kenntnis", referencedColumnName = "bezeichnung", nullable = false))
    public List<Kenntnis> getKenntnisse() {
        return kenntnisse;
    }

    public void addKenntnis(Kenntnis kenntnis) {
        if (this.kenntnisse == null){
            this.kenntnisse = new ArrayList<>();
        }
        if (this.kenntnisse.contains(kenntnis)){
            return;
        }
        this.kenntnisse.add(kenntnis);
    }

    // student_interessiert_sich_fuer_taetigkeitsfeld
    @JoinTable(name = "student_interessiert_sich_fuer_taetigkeitsfeld", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "taetigkeitsfeld", referencedColumnName = "bezeichnung", nullable = false))
    public List<Taetigkeitsfeld> getTaetigkeitsfelder() {
        return taetigkeitsfelder;
    }

    // student_favorisiert_stellenanzeige


    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_favorisiert_stellenanzeige", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "studentId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "stellenanzeigeId", nullable = false))
    public List<Stellenanzeige> stellenanzeigenFavourisiert;


    public void addStellenanzeige(Stellenanzeige stellenanzeige) {
        if (this.stellenanzeigen == null){
            this.stellenanzeigen = new ArrayList<>();
        }
        if (this.stellenanzeigen.contains(stellenanzeige)){
            return;
        }
        this.stellenanzeigen.add(stellenanzeige);
    }

    // student_beherrscht_sprache
    @JoinTable(name = "student_beherrscht_sprache", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "sprache_id", referencedColumnName = "id", nullable = false))
    public List<Sprache> getSprachen() {
        return sprachen;
    }

    public void addSprache(Sprache sprache) {
        if (this.sprachen == null){
            this.sprachen = new ArrayList<>();
        }
        if (this.sprachen.contains(sprache)){
            return;
        }
        this.sprachen.add(sprache);
    }

    // student_bewirbt_sich
    @JoinTable(name = "student_bewirbt_sich", catalog = "nmuese2s",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewerbung_id", referencedColumnName = "id", nullable = false))
    public List<Bewerbung> getBewerbungen() {return bewerbungen;}
    
    public void addBewertung(Bewertung bewertung) {
        if (this.bewertungen == null){
            this.bewertungen = new ArrayList<>();
        }
        if (this.bewertungen.contains(bewertung)){
            return;
        }
        this.bewertungen.add(bewertung);
    }



    // student_bewertet_unternehmen
    @JoinTable(name = "student_bewertet_unternehmen", catalog = "nmuese2s",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewertung_id", referencedColumnName = "id", nullable = false))
    public List<Bewertung> getBewertungen() {return bewertungen;}
    
    public void addBewerbung(Bewerbung bewerbung) {
        if (this.bewerbungen == null){
            this.bewerbungen = new ArrayList<>();
        }
        if (this.bewerbungen.contains(bewerbung)){
            return;
        }
        this.bewerbungen.add(bewerbung);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Student student)) return false;
        return studentId == student.studentId
                && Objects.equals(vorname, student.vorname)
                && Objects.equals(nachname, student.nachname)
                && Objects.equals(matrikelNummer, student.matrikelNummer)
                && Objects.equals(studiengang, student.studiengang)
                && Objects.equals(studienbeginn, student.studienbeginn)
                && Objects.equals(geburtsdatum, student.geburtsdatum)
                && Objects.equals(lebenslauf, student.lebenslauf)
                && Objects.equals(user, student.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, vorname, nachname, matrikelNummer, studiengang, studienbeginn, geburtsdatum, lebenslauf, user);
    }

    public void addTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if(taetigkeitsfelder == null) {
            taetigkeitsfelder = new ArrayList<>();
        }
        if (taetigkeitsfelder.contains(taetigkeitsfeld)) {
            return;
        }
        taetigkeitsfelder.add(taetigkeitsfeld);
        taetigkeitsfeld.addStudent(this);
    }

    public void removeStellenanzeige(Stellenanzeige stellenanzeige) {
    }
}
