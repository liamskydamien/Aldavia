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
    @OneToOne(optional = false)
    private User user;

    @OneToMany
    private List<Bewertung> bewertungen;

    @OneToMany
    private List<Bewerbung> bewerbungen;

    @ManyToMany
    private List<Kenntnis> kenntnisse;

    @ManyToMany
    private List<Qualifikation> qualifikationen;

    @ManyToMany
    private List<Taetigkeitsfeld> taetigkeitsfelder;

    @ManyToMany
    private List<Stellenanzeige> stellenanzeigen;

    @ManyToMany
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

    // student_hat_kenntnis
    @JoinTable(name = "student_hat_kenntnis", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "kenntnis", referencedColumnName = "bezeichnung", nullable = false))
    public List<Kenntnis> getKenntnisse() {
        return kenntnisse;
    }

    // student_interessiert_sich_fuer_taetigkeitsfeld
    @JoinTable(name = "student_interessiert_sich_fuer_taetigkeitsfeld", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "taetigkeitsfeld", referencedColumnName = "bezeichnung", nullable = false))
    public List<Taetigkeitsfeld> getTaetigkeitsfelder() {
        return taetigkeitsfelder;
    }

    // student_favorisiert_stellenanzeige
    @JoinTable(name = "student_favorisiert_stellenanzeige", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "stellenanzeige", referencedColumnName = "id", nullable = false))
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }

    // student_beherrscht_sprache
    @JoinTable(name = "student_beherrscht_sprache", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "sprache_id", referencedColumnName = "id", nullable = false))
    public List<Sprache> getSprachen() {
        return sprachen;
    }

    // student_bewirbt_sich
    @JoinTable(name = "student_bewirbt_sich", catalog = "nmuese2s",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewerbung_id", referencedColumnName = "id", nullable = false))
    public List<Bewerbung> getBewerbungen() {return bewerbungen;}

    // student_bewertet_unternehmen
    @JoinTable(name = "student_bewertet_unternehmen", catalog = "nmuese2s",
            schema = "carlook",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bewertung_id", referencedColumnName = "id", nullable = false))
    public List<Bewertung> getBewertungen() {return bewertungen;}

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
}
