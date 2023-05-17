package org.hbrs.se2.project.aldavia.entities;

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
public class Sprache {
    private String name;
    private String level;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int spracheId;

    @Basic
    @Column(name = "name")
    public String getName() { return name; }

    @Basic
    @Column(name = "level")
    public String getLevel() { return level; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprache sprache = (Sprache) o;
        return spracheId == sprache.spracheId &&
                Objects.equals(sprache, sprache.name) &&
                Objects.equals(level, sprache.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spracheId, name, level);
    }


    @ManyToMany(mappedBy = "sprachen", fetch = FetchType.EAGER )
    private List<Student> studenten;
    public List<Student> getStudenten() {
        return studenten;
    }

}
