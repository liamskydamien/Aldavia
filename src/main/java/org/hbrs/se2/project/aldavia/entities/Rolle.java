package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rolle", schema = "test_schema")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rolle {

    @Id
    @Basic
    @Column(name = "bezeichnung")
    private String bezeichnung;

    @ManyToMany
    private List<User> users;

    /**
     * Adds a user to the list of users
     * @param user the user to add
     */
    public void addUser(User user) {
        if(users == null) {
            users = new ArrayList<>();
        }
        if(!this.users.contains(user)) {
            this.users.add(user);
            user.addRolle(this);
        }
    }

    public void removeUser(User user) {
        if(users == null) {
            return;
        }
        if(this.users.contains(user)) {
            this.users.remove(user);
            user.removeRolle(this);
        }
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolle rolle = (Rolle) o;
        return Objects.equals(bezeichnung, rolle.bezeichnung) && Objects.equals(users, rolle.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichnung, users);
    }
}