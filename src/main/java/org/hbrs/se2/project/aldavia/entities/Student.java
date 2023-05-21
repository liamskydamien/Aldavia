package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "student", schema = "aldavia_new")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "nachname", nullable = false)
    private String nachname;

    @Basic
    @Column(name = "vorname", nullable = false)
    private String vorname;

    @Basic
    @Column(name = "matrikelnummer", unique = true)
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

    // student_hat_user

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // student_hat_kenntnis

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST)
    private List<Kenntnis> kenntnisse;

    @JoinTable(
            name = "student_to_kenntnis",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "kenntnis_id", referencedColumnName = "bezeichnung", nullable = false),
            schema = "test_schema"
    )
    public List<Kenntnis> getKenntnisse() {
        return kenntnisse;
    }

    public void addKenntnis(Kenntnis kenntnis) {
        if (kenntnisse == null) {
            kenntnisse = new ArrayList<>();
        }
        if (!this.kenntnisse.contains(kenntnis)) {
            this.kenntnisse.add(kenntnis);
            kenntnis.addStudent(this);
        }
    }

    public void removeKenntnis(Kenntnis kenntnis) {
        if (kenntnisse == null) {
            return;
        }
        if (this.kenntnisse.contains(kenntnis)) {
            this.kenntnisse.remove(kenntnis);
            kenntnis.removeStudent(this);
        }
    }

    // student_hat_qualifikation

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Qualifikation> qualifikationen;

    @JoinTable(
            name = "student_to_qualifikation",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "qualifikation_id", referencedColumnName = "id", nullable = false),
            schema = "test_schema"
    )
    public List<Qualifikation> getQualifikationen() {
        return qualifikationen;
    }

    public void addQualifikation(Qualifikation qualifikation) {
        if (qualifikationen == null) {
            qualifikationen = new ArrayList<>();
        }
        if (!this.qualifikationen.contains(qualifikation)) {
            this.qualifikationen.add(qualifikation);
            qualifikation.setStudent(this);
        }
    }

    public void removeQualifikation(Qualifikation qualifikation) {
        if (qualifikationen == null) {
            return;
        }
        if (this.qualifikationen.contains(qualifikation)) {
            this.qualifikationen.remove(qualifikation);
            qualifikation.setStudent(null);
        }
    }

    // student_hat_sprachen

    @ManyToMany(mappedBy = "students",cascade = CascadeType.PERSIST)
    private List<Sprache> sprachen;

    @JoinTable(
            name = "student_to_sprache",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "sprache_id", referencedColumnName = "id", nullable = false),
            schema = "test_schema"
    )
    public List<Sprache> getSprachen() {
        return sprachen;
    }

    public void addSprache(Sprache sprache) {
        if (sprachen == null) {
            sprachen = new ArrayList<>();
        }
        if (!this.sprachen.contains(sprache)) {
            this.sprachen.add(sprache);
            sprache.addStudent(this);
        }
    }

    public void removeSprache(Sprache sprache) {
        if (sprachen == null) {
            return;
        }
        if (this.sprachen.contains(sprache)) {
            this.sprachen.remove(sprache);
            sprache.removeStudent(this);
        }
    }

    // student_interssiert_sich_fuer_taetigkeitsfeld

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST)
    private List<Taetigkeitsfeld> taetigkeitsfelder;

    @JoinTable(
            name = "student_to_taetigkeitsfeld",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "taetigkeitsfeld_id", referencedColumnName = "bezeichnung", nullable = false),
            schema = "test_schema"
    )
    public List<Taetigkeitsfeld> getTaetigkeitsfelder() {
        return taetigkeitsfelder;
    }

    public void addTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfelder == null) {
            taetigkeitsfelder = new ArrayList<>();
        }
        if (!this.taetigkeitsfelder.contains(taetigkeitsfeld)) {
            this.taetigkeitsfelder.add(taetigkeitsfeld);
            taetigkeitsfeld.addStudent(this);
        }
    }

    public void removeTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfelder == null) {
            return;
        }
        if (this.taetigkeitsfelder.contains(taetigkeitsfeld)) {
            this.taetigkeitsfelder.remove(taetigkeitsfeld);
            taetigkeitsfeld.removeStudent(this);
        }
    }

    // student_beweirbt_sich_auf_stellenanzeige

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Bewerbung> bewerbungen;

    @JoinTable(
            name = "student_to_bewerbung",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewerbung_id", referencedColumnName = "id", nullable = false),
            schema = "test_schema"
    )
    public List<Bewerbung> getBewerbungen() {
        return bewerbungen;
    }

    public void addBewerbung(Bewerbung bewerbung) {
        if (bewerbungen == null) {
            bewerbungen = new ArrayList<>();
        }
        if (!this.bewerbungen.contains(bewerbung)) {
            this.bewerbungen.add(bewerbung);
            bewerbung.setStudent(this);
        }
    }

    public void removeBewerbung(Bewerbung bewerbung){
        if (bewerbungen == null) {
            return;
        }
        if(bewerbungen.contains(bewerbung)){
            bewerbungen.remove(bewerbung);
            bewerbung.setStudent(null);
        }
    }

    // Methoden

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(nachname, student.nachname) && Objects.equals(vorname, student.vorname) && Objects.equals(matrikelNummer, student.matrikelNummer) && Objects.equals(studiengang, student.studiengang) && Objects.equals(studienbeginn, student.studienbeginn) && Objects.equals(geburtsdatum, student.geburtsdatum) && Objects.equals(lebenslauf, student.lebenslauf) && Objects.equals(user, student.user) && Objects.equals(kenntnisse, student.kenntnisse) && Objects.equals(qualifikationen, student.qualifikationen) && Objects.equals(sprachen, student.sprachen) && Objects.equals(taetigkeitsfelder, student.taetigkeitsfelder) && Objects.equals(bewerbungen, student.bewerbungen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nachname, vorname, matrikelNummer, studiengang, studienbeginn, geburtsdatum, lebenslauf, user, kenntnisse, qualifikationen, sprachen, taetigkeitsfelder, bewerbungen);
    }
}