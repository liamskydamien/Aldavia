package org.hbrs.se2.project.aldavia.entities;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Table( name ="rolle" , schema = "carlook" )
@NoArgsConstructor


public class Rolle {
    private String bezeichnung;
    private List<User> users;

    public Rolle(String roleStudent) {
        bezeichnung = roleStudent;
    }

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

    @ManyToMany(mappedBy = "roles")
    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (user == null) {
            users = null;
        } else {
            if (this.users.contains(user))
                return;
            this.users.add(user);
            user.addRolle(this);
        }
    }

    public void removeUser(User user) {
        if (user != null) {
            if (!this.users.contains(user))
                return;
            this.users.remove(user);
        }
    }

}
