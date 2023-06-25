package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;


import java.util.ArrayList;
import java.util.List;

public class AdressenDTOFactory {
    private static AdressenDTOFactory instance;
    public static synchronized AdressenDTOFactory getInstance() {
        if (instance == null) {
            instance = new AdressenDTOFactory();
        }
        return instance;
    }
    private AdressenDTOFactory() {}

    public AdresseDTO createAdressenDTO(Adresse adressen) {
        return AdresseDTO.builder()
                .id(adressen.getId())
                .strasse(adressen.getStrasse())
                .hausnummer(adressen.getHausnummer())
                .plz(adressen.getPlz())
                .ort(adressen.getOrt())
                .land(adressen.getLand())
                .build();
    }




}
