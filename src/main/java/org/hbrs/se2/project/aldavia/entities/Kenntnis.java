package org.hbrs.se2.project.aldavia.entities;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="kenntnis" , schema = "carlook" )
@NoArgsConstructor
@Setter
@Getter
public class Kenntnis  {
    private String bezeichnung;
    private List<Student> studenten;
    @Id
    @Column(name = "bezeichnung", nullable = false, unique = true)
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kenntnis kenntnis = (Kenntnis) o;
        return Objects.equals(bezeichnung, kenntnis.bezeichnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }

    @ManyToMany(mappedBy = "kenntnisse", fetch = FetchType.EAGER)
    public List<Student> getStudenten() {return studenten;}

    public void addStudent(Student student) {
        if (studenten == null)
            studenten = new ArrayList<>();
        if (this.studenten.contains(student))
            return;
        this.studenten.add(student);
    }

    public void removeStudent(Student student) {
        if (studenten == null)
            studenten = new ArrayList<>();
        if (!this.studenten.contains(student))
            return;
        this.studenten.remove(student);
    }
}