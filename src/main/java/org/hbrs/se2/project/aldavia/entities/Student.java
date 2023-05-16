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

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private int studentId;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Kenntnis> kenntnisse;

    @JoinTable(name = "student_to_kenntnis", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "studentId", referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung"))
    public List<Kenntnis> getKenntnisse() {
        return kenntnisse;
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


}
