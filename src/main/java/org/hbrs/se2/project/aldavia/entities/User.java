package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
// import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
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
    @Column(name = "userid", nullable = false, unique = true)
    private String userid;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "profile_picture")
    private String profilePicture;

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Rolle> rollen;
    @JoinTable(
            name = "user_to_rolle",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "rolle", referencedColumnName = "bezeichnung", nullable = false)
    )
    public List<Rolle> getRollen() {
        return rollen;
    }

    public void addRolle(Rolle rolle) {
        if (rollen == null) {
            rollen = new ArrayList<>();
        }
        if (!this.rollen.contains(rolle)) {
            this.rollen.add(rolle);
            rolle.addUser(this);
        }
    }

    public void removeRolle(Rolle rolle) {
        if (rollen == null) {
            return;
        }
        if (this.rollen.contains(rolle)) {
            this.rollen.remove(rolle);
            rolle.removeUser(this);
        }
    }

    // Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(userid, user.userid) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(profilePicture, user.profilePicture) && Objects.equals(beschreibung, user.beschreibung) && Objects.equals(rollen, user.rollen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, password, email, phone, profilePicture, beschreibung, rollen);
    }
}