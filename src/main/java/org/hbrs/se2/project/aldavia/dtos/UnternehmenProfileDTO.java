package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

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
    private Set<StellenanzeigeDTO> stellenanzeigen;
    private Set<AdresseDTO> adressen;
    private String profilePicture;

}
