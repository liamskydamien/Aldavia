package org.hbrs.se2.project.aldavia.entities;

import lombok.*;

import javax.persistence.*;
// import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="user" , schema = "carlook" )
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class User {
    private int id;
    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "password")
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

    private List<Rolle> roles;

    @ManyToMany
    @JoinTable(name = "user_to_rolle", catalog = "nmuese2s", schema = "carlook",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
    public List<Rolle> getRoles() {
        return roles;
    }

    @Basic
    @Column(name = "beschreibung")
    private String beschreibung;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }
    @Basic
    @Column(name = "userid", nullable = false, length = 45, unique = true)
    public String getUserid() {
        return userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
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
