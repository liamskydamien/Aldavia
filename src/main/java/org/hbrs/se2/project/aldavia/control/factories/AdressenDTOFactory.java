package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;


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
    public AdressenDTOFactory() {}

    public AdresseDTO createAdressenDTO(Adresse adressen) {
        return AdresseDTO.builder()
                .id(adressen.getId())
                .strasse(adressen.getStrasse())
                .hausnummer(adressen.getHausnummer())
                .plz(adressen.getPlz())
                .ort(adressen.getOrt())
                .land(adressen.getLand())
                .unternehmen(createUnternehmenProfileDTO(adressen))
                .build();
    }

    public List<UnternehmenProfileDTO> createUnternehmenProfileDTO(Adresse adressen) {
        if(adressen.getUnternehmen() != null && !adressen.getUnternehmen().isEmpty()) {
            return adressen.getUnternehmen().stream()
                    .map(UnternehmenProfileDTOFactory.getInstance()::createUnternehmenProfileDTO)
                    .collect(java.util.stream.Collectors.toList());
        }
        return new ArrayList<>();
    }



}
