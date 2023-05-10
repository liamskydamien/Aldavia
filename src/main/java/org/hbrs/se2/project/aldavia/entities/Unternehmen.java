package org.hbrs.se2.project.aldavia.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="unternehmen" , schema = "aldavia" )
public class Unternehmen extends Benutzer {
    private String firmenname;
    private String bereich;
    private String webseite;
    private String beschreibung;
    private List<Adresse> adressen;
    private List<Stellenanzeige> stellenanzeigen;

    @Embedded
    private Ansprechpartner ansprechpartner;
    @Embeddable
    public static class Ansprechpartner {
        @Column(name = "ap_vorname")
        private String vorname;

        @Column(name = "ap_nachname")
        private String nachname;

        @Column(name = "ap_telefonnummer")
        private String telefonnummer;

        @Basic
        @Column(name = "ap_vorname")
        public String getVorname() {
            return vorname;
        }
        public void setVorname(String lebenslauf) {
            this.vorname = vorname;
        }

        @Basic
        @Column(name = "ap_nachname")
        public String getNachname() {
            return nachname;
        }
        public void setNachname(String nachname) {
            this.nachname = nachname;
        }

        @Basic
        @Column(name = "ap_telefonnummer")
        public String getTelefonnummer() {
            return telefonnummer;
        }
        public void setTelefonnummer(String telefonnummer) {
            this.telefonnummer = telefonnummer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Ansprechpartner ansprechpartner = (Ansprechpartner) o;
            return Objects.equals(vorname, ansprechpartner.vorname) &&
                    Objects.equals(nachname, ansprechpartner.nachname) &&
                    Objects.equals(telefonnummer, ansprechpartner.telefonnummer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vorname, nachname, telefonnummer);
        }
    }

    @Basic
    @Column(name = "firmenname")
    public String getFirmenname() {
        return firmenname;
    }
    public void setFirmenname(String firmenname) {this.firmenname = firmenname;}

    @Basic
    @Column(name = "bereich")
    public String getBereich() {
        return bereich;
    }
    public void setBereich(String bereich) {this.bereich = bereich;}

    @Basic
    @Column(name = "webseite")
    public String getWebseite() {
        return webseite;
    }
    public void setWebseite(String webseite) {
        this.webseite = webseite;
    }

    @Basic
    @Column(name = "beschreibung")
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unternehmen unternehmen = (Unternehmen) o;
        return Objects.equals(firmenname, unternehmen.firmenname) &&
                Objects.equals(bereich, unternehmen.bereich) &&
                Objects.equals(webseite, unternehmen.webseite) &&
                Objects.equals(beschreibung, unternehmen.beschreibung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmenname, bereich, webseite, beschreibung);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "unternehmen_hat_adresse", catalog = "demouser",
            schema = "aldavia",
            joinColumns = @JoinColumn(name = "benutzername", referencedColumnName = "benutzername", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichnung", nullable = false))
    public List<Adresse> getAdressen() {
        return adressen;
    }
    public void setAdressen(List<Adresse> adressen) {this.adressen = adressen;}

    @OneToMany(mappedBy = "unternehmen")
    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigen;
    }
    public void setStellenanzeigen(List<Stellenanzeige> stellenanzeigen) {this.stellenanzeigen = stellenanzeigen;}

}
