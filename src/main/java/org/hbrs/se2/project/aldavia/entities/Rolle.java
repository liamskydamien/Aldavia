package org.hbrs.se2.project.aldavia.entities;

import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Table( name ="rolle" , schema = "carlook" )
public class Rolle {
    private String bezeichnung;
    private List<User> users;

    @Id
    @Column(name = "bezeichnung")
    public String getBezeichnung() {
        return bezeichnung;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolle rolle = (Rolle) o;
        return Objects.equals(bezeichnung, rolle.bezeichnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung);
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER )
    public List<User> getUsers() {
        return users;
    }

}
