package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table( name ="user" , schema = "aldavia" )
public class Benutzer {
//    private int id;
    private String benutzername;
    private String email;
    private String passwort;
    private String profilbild;
    private int benutzerid;
    private List<Rolle> rollen;

//    @Id
//    @GeneratedValue
//    @Column(name = "id")
//    public int getId() {return id;}
//    public void setId(int id) {this.id = id;}

    @Id
    @Column(name = "benutzername")
    public String getBenutzername() {return benutzername;}
    public void setBenutzername(String userid) {this.benutzername = benutzername;}

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "passwort")
    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benutzer benutzer = (Benutzer) o;
        return Objects.equals(benutzername, benutzer.benutzername) &&
                Objects.equals(email, benutzer.email) &&
                Objects.equals(passwort, benutzer.passwort) &&
                Objects.equals(profilbild, benutzer.profilbild);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benutzername, email, passwort, profilbild);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "benutzer_hat_rolle", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
    public List<Rolle> getRollen() {
        return rollen;
    }

    public void setRollen(List<Rolle> rollen) {this.rollen = rollen;}
}
