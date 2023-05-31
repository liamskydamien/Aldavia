package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;

public class UnternehmenProfileDTOFactory {

    private UnternehmenProfileDTOFactory() {
    }

    private static UnternehmenProfileDTOFactory factory;

    public static synchronized UnternehmenProfileDTOFactory getInstance() {
        if (factory == null) {
            factory = new UnternehmenProfileDTOFactory();
        }
        return factory;
    }

    public UnternehmenProfileDTO createUnternehmenProfileDTO(Unternehmen unternehmen) {
        User user = unternehmen.getUser();

        UnternehmenProfileDTO dto = UnternehmenProfileDTO.builder()
                .name(unternehmen.getName())
                .email(user.getEmail())
                .beschreibung(user.getBeschreibung())
                .telefonnummer(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .ap_nachname(unternehmen.getAp_nachname())
                .ap_vorname(unternehmen.getAp_vorname())
                .adressen(unternehmen.getAdressen())
                .password(user.getPassword())
                .profilbild(user.getProfilePicture())
                .webside(unternehmen.getWebseite())
                .adressen(unternehmen.getAdressen())
                .stellenanzeigen(unternehmen.getStellenanzeigen())
                .build();

        return dto;

    }


}
