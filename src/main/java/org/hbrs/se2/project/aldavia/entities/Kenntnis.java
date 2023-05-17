package org.hbrs.se2.project.aldavia.entities;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "kenntnisse", schema = "carlook")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Kenntnis {
    private String bezeichnung;
    private List<Student> students;
    @Id
    @Column(name = "kenntnis", nullable = false, unique = true)
    public String getBezeichnung() {
        return bezeichnung;
    }

    @ManyToMany(mappedBy = "kenntnisse", cascade = CascadeType.ALL)
    private List<Student> getStudents(){
        return students;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kenntnis kenntnis = (Kenntnis) o;
        return Objects.equals(bezeichnung, kenntnis.bezeichnung) && Objects.equals(students, kenntnis.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, students);
    }
}
