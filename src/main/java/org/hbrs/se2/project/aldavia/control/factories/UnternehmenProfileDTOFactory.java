package org.hbrs.se2.project.aldavia.control.factories;

import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class UnternehmenProfileDTOFactory {

    private final Logger logger = LoggerFactory.getLogger(UnternehmenProfileDTOFactory.class);
    private final AdressenDTOFactory adressenDTOFactory = AdressenDTOFactory.getInstance();
    private UnternehmenProfileDTOFactory() {
    }

    private static UnternehmenProfileDTOFactory factory;

    public static synchronized UnternehmenProfileDTOFactory getInstance() {
        if (factory == null) {
            factory = new UnternehmenProfileDTOFactory();
        }
        return factory;
    }

    @SneakyThrows
    public UnternehmenProfileDTO createUnternehmenProfileDTO(Unternehmen unternehmen) {
        try{
            User user = unternehmen.getUser();
            UnternehmenProfileDTO unternehmenProfileDTO = UnternehmenProfileDTO.builder()
                    .name(unternehmen.getName())
                    .username(user.getUserid())
                    .email(user.getEmail())
                    .beschreibung(unternehmen.getBeschreibung())
                    .telefonnummer(user.getPhone())
                    .profilePicture(user.getProfilePicture())
                    .ap_nachname(unternehmen.getAp_nachname())
                    .ap_vorname(unternehmen.getAp_vorname())
                    .password(user.getPassword())
                    .profilbild(user.getProfilePicture())
                    .webside(unternehmen.getWebseite())
                    .build();

            Set<StellenanzeigeDTO> stellenanzeigenDTO = createStellenanzeigeDTOList(unternehmenProfileDTO,unternehmen);
            unternehmenProfileDTO.setStellenanzeigen(stellenanzeigenDTO);
            Set<AdresseDTO> adressenDTO = createAdresseList(unternehmen);
            unternehmenProfileDTO.setAdressen(adressenDTO);
            return unternehmenProfileDTO;

        } catch (Exception e) {
            throw new ProfileException("Fehler beim Erstellen des Unternehmensprofils", ProfileException.ProfileExceptionType.ERROR_CREATING_PROFILE_DTO);
        }


    }

    private Set<StellenanzeigeDTO> createStellenanzeigeDTOList(UnternehmenProfileDTO unternehmenDTO, Unternehmen unternehmen) {
        if(unternehmen.getStellenanzeigen() != null && !unternehmen.getStellenanzeigen().isEmpty()) {
            Set<Stellenanzeige> stellenanzeigen = unternehmen.getStellenanzeigen();
            Set<StellenanzeigeDTO> stellenanzeigenDTOs = new HashSet<>();
            for (Stellenanzeige stellenanzeige : stellenanzeigen) {
                StellenanzeigeDTO stellenanzeigeDTO = StellenanzeigeDTOFactory.getInstance().createStellenanzeigeDTOCompanyKnown(stellenanzeige, unternehmenDTO);
                stellenanzeigenDTOs.add(stellenanzeigeDTO);
            }
            logger.info("STELLENANZEIGEDTO WURDE ERSTELLT. GROESSE: " + stellenanzeigenDTOs.size() + " INHALT: " + stellenanzeigenDTOs);
            return stellenanzeigenDTOs;
        }
        return new HashSet<>();
    }

    private Set<AdresseDTO> createAdresseList(Unternehmen unternehmen) {
        if(unternehmen.getAdressen() != null && !unternehmen.getAdressen().isEmpty()){
            Set<AdresseDTO> adressenDTOs = new HashSet<>();
            for (Adresse adresse : unternehmen.getAdressen()) {
                AdresseDTO adresseDTO = adressenDTOFactory.createAdressenDTO(adresse);
                adressenDTOs.add(adresseDTO);
            }
            return adressenDTOs;
        }
        return new HashSet<>();

    }





}
