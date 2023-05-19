package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    private int studentId;

    @Basic
    @Column(name = "vorname")
    private String vorname;

    @Basic
    @Column(name = "nachname")
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

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private User user;


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
