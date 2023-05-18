package org.hbrs.se2.project.aldavia.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
// import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="sprache" , schema = "carlook" )
@NoArgsConstructor
@Setter
@Getter
public class Sprache {
    private String name;
    private String level;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int spracheId;

    @Basic
    @Column(name = "name")
    public String getName() { return name; }

    @Basic
    @Column(name = "level")
    public String getLevel() { return level; }


    @ManyToMany(mappedBy = "sprachen", fetch = FetchType.EAGER )
    private List<Student> studenten;
    public List<Student> getStudenten() {
        return studenten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprache sprache = (Sprache) o;
        return Objects.equals(name, sprache.name) && Objects.equals(level, sprache.level) && Objects.equals(spracheId, sprache.spracheId) && Objects.equals(studenten, sprache.studenten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, spracheId, studenten);
    }
}
