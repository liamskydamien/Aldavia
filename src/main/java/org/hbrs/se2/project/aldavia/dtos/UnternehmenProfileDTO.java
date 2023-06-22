package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnternehmenProfileDTO {
    private String username;
    private String name;
    private String password;
    private String beschreibung;
    private String ap_vorname;
    private String ap_nachname;
    private String webside;
    private String email;
    private String telefonnummer;
    private String profilbild;
    private Set<Stellenanzeige> stellenanzeigen;
    private Set<Adresse> adressen;
    private String profilePicture;

}
