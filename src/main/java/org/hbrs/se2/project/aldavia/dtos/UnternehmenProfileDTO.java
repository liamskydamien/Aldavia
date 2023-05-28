package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnternehmenProfileDTO {
    private String name;
    private String password;
    private String beschreibung;
    private String ap_vorname;
    private String ap_nachname;
    private String webside;
    private String email;
    private String telefonnummer;
    private String profilbild;
    private List<Stellenanzeige> stellenanzeigen;
    private List<Adresse> adressen;

    private String profilePicture;

}
