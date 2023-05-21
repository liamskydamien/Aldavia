package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
// import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="user" , schema = "aldavia" )
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "userid", nullable = false, unique = true)
    private String userid;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "profile_picture")
    private String profilePicture;

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rolle> roles;
    @JoinTable(name = "user_to_rolle", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
    public List<Rolle> getRoles() {
        return roles;
    }

    public void addRolle(Rolle rolle) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
            this.roles.add(rolle);
            rolle.addUser(this);
        } else {
            if (this.roles.contains(rolle)) {
                return;
            }
            this.roles.add(rolle);
            rolle.addUser(this);
        }
    }

    public void removeRolle(Rolle rolle) {
        if (this.roles == null) {
            roles = new ArrayList<>();
        } else {
            if (!this.roles.contains(rolle)) {
                return;
            }
            this.roles.remove(rolle);
            rolle.removeUser(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user  = (User) o;
        return id == user.id
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(userid, user.userid)
                && Objects.equals(phone, user.phone)
                && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, userid, phone, roles);
    }
}
