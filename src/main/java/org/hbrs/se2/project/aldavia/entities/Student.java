package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "carlook")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int studentId;

    @Basic
    @Column(name = "vorname")
    private String vorname;

    @Basic
    @Column(name = "nachname")
    private String nachname;
    @Basic
    @Column(name = "matrikelnummer", nullable = false, unique = true)
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

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private User user;

    public Student(String vorname, String nachname, String matrikelNummer, String studiengang, LocalDate studienbeginn, LocalDate geburtsdatum, String lebenslauf) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.matrikelNummer = matrikelNummer;
        this.studiengang = studiengang;
        this.studienbeginn = studienbeginn;
        this.geburtsdatum = geburtsdatum;
        this.lebenslauf = lebenslauf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId && Objects.equals(vorname, student.vorname) && Objects.equals(nachname, student.nachname) && Objects.equals(matrikelNummer, student.matrikelNummer) && Objects.equals(studiengang, student.studiengang) && Objects.equals(studienbeginn, student.studienbeginn) && Objects.equals(geburtsdatum, student.geburtsdatum) && Objects.equals(lebenslauf, student.lebenslauf) && Objects.equals(user, student.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, vorname, nachname, matrikelNummer, studiengang, studienbeginn, geburtsdatum, lebenslauf, user);
    }

    // student_hat_qualifikation
    @ManyToMany
    private List<Qualifikation> qualifikationen;
    @JoinTable(name = "student_hat_qualifikation", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "qualifikation", referencedColumnName = "bezeichnung", nullable = false))
    public List<Qualifikation> getQualifikationen() {
        return qualifikationen;
    }
    public void setQualifikationen(List<Qualifikation> qualifikationen) {
        this.qualifikationen = qualifikationen;
    }

    // student_hat_kenntnis
    @ManyToMany
    private List<Kenntnis> kenntnisse;
    @JoinTable(name = "student_hat_kenntnis", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "kenntnis", referencedColumnName = "bezeichnung", nullable = false))
    public List<Kenntnis> getKenntnisse() {
        return kenntnisse;
    }
    public void setKenntnisse(List<Kenntnis> kenntnisse) {
        this.kenntnisse = kenntnisse;
    }

    // student_interessiert_sich_fuer_taetigkeitsfeld
    @ManyToMany
    private List<Taetigkeitsfeld> taetigkeitsfelder;
    @JoinTable(name = "student_interessiert_sich_fuer_taetigkeitsfeld", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "taetigkeitsfeld", referencedColumnName = "bezeichnung", nullable = false))
    public List<Taetigkeitsfeld> getTaetigkeitsfelder() {
        return taetigkeitsfelder;
    }
    public void setTaetigkeitsfelder(List<Taetigkeitsfeld> taetigkeitsfelder) {
        this.taetigkeitsfelder = taetigkeitsfelder;
    }

    // student_favorisiert_stellenanzeige
    @ManyToMany
    private List<Stellenanzeige> stellenanzeigen;
    @JoinTable(name = "student_favorisiert_stellenanzeige", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "stellenanzeige", referencedColumnName = "id", nullable = false))
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }
    public void setStellenanzeigen(List<Stellenanzeige> stellenanzeigen) {
        this.stellenanzeigen = stellenanzeigen;
    }

    // student_beherrscht_sprache
    @ManyToMany
    private List<Sprache> sprachen;
    @JoinTable(name = "student_beherrscht_sprache", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "sprache_id", referencedColumnName = "id", nullable = false))
    public List<Sprache> getSprachen() {
        return sprachen;
    }
    public void setSprachen(List<Sprache> sprachen) {
        this.sprachen = sprachen;
    }

    // student_bewirbt_sich
    @OneToMany
    private List<Bewerbung> bewerbungen;
    @JoinTable(name = "student_bewirbt_sich", catalog = "nmuese2s",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewerbung_id", referencedColumnName = "id", nullable = false))
    public List<Bewerbung> getBewerbungen() {return bewerbungen;}
    public void setBewerbungen(List<Bewerbung> bewerbungen) {this.bewerbungen = bewerbungen;}

    // student_bewertet_unternehmen
    @OneToMany
    private List<Bewertung> bewertungen;
    @JoinTable(name = "student_bewertet_unternehmen", catalog = "nmuese2s",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewertung_id", referencedColumnName = "id", nullable = false))
    public List<Bewertung> getBewertungen() {return bewertungen;}
    public void setBewertungen(List<Bewertung> bewertungen) {this.bewertungen = bewertungen;}
}
