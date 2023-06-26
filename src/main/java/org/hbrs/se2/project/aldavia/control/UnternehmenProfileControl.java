package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.service.AdresseService;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.UnternehmenProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class UnternehmenProfileControl {
    private final UnternehmenService unternehmenService;

    private final UnternehmenProfileDTOFactory unternehmenProfileDTOFactory;
    private final StellenanzeigenService stellenanzeigenService;
    private final AdresseService adresseService;
    private final Logger logger = LoggerFactory.getLogger(UnternehmenProfileControl.class);


    public UnternehmenProfileDTO getUnternehmenProfileDTO(String userName) throws ProfileException {
        try {
            logger.info("Getting Company from Database with username: " + userName);
            Unternehmen unternehmen = unternehmenService.getUnternehmen(userName);
            logger.info("Found a company to the userName with the following company name: " + unternehmen.getName());
            UnternehmenProfileDTO dto = unternehmenProfileDTOFactory.createUnternehmenProfileDTO(unternehmen);
            return dto;
        } catch (Exception e) {
            throw e;
        }
    }

    /*public void createAndUpdateUnternehmenProfile(UnternehmenProfileDTO dto, String userName) throws ProfileException {
        logger.info("Getting Company from Database to update its information: " + userName);
        Unternehmen unternehmen = unternehmenService.getUnternehmen(userName);
        logger.info("Found a company to the userName with the following username: " + unternehmen.getUser().getUserid());
        try {
            unternehmenService.updateUnternehmenInformation(unternehmen,dto);

        } catch(Exception e) {
            throw e;
        }
        logger.info("Sucessfully updatet the information of the company: " + unternehmen.getName());
    }
*/

    public void createAndUpdateUnternehmenProfile(UnternehmenProfileDTO dto, String userName) throws ProfileException {
        logger.info("Getting Company from Database to update its information: " + userName);
        Unternehmen unternehmen = unternehmenService.getUnternehmen(userName);
        logger.info("Found a company to the userName with the following username: " + unternehmen.getUser().getUserid());

        //Change Data in Unternhemen
        unternehmen.setName(dto.getName());
        unternehmen.setBeschreibung(dto.getBeschreibung());
        unternehmen.setWebseite(dto.getWebside());
        unternehmen.setAp_nachname(dto.getAp_nachname());
        unternehmen.setAp_vorname(dto.getAp_vorname());
        logger.info("Sucessfully updatet the information of the company: " + unternehmen.getName());

        //Remove Attributes
        removeAttributes(unternehmen);

        //Add Attributes
        addAttributes(unternehmen, dto);

        //Save Unternehmen
        logger.info("Updating Unternehmen in Database: " + unternehmen.getName());
        unternehmenService.createOrUpdateUnternehmen(unternehmen);
        logger.info("Sucessfully updated the information of the company: " + unternehmen.getName());
    }

    private  void removeAttributes(Unternehmen unternehmen){
        Set<Stellenanzeige> stellenanzeigeSet = unternehmen.getStellenanzeigen() != null ? new HashSet<>(unternehmen.getStellenanzeigen()): new HashSet<>();
        Set<Adresse> adresseSet = unternehmen.getAdressen() != null ? new HashSet<>(unternehmen.getAdressen()): new HashSet<>();

        //Remove Stellenanzeigen
        for(Stellenanzeige stellenanzeige : stellenanzeigeSet){
            stellenanzeigenService.deleteStellenanzeige(stellenanzeige);
        }

       // Remove Adressen
        for (Adresse adresse : adresseSet){
            adresseService.removeUnternehmenFromAdresse(adresse, unternehmen);
        }
    }

    @SneakyThrows
    private void addAttributes(Unternehmen unternehmen, UnternehmenProfileDTO dto){
        //Add Stellenanzeigen
        if(dto.getStellenanzeigen() != null){
            for(StellenanzeigeDTO stellenanzeige : dto.getStellenanzeigen()){
                stellenanzeigenService.addStellenanzeige(stellenanzeige, unternehmen);
            }
        }

        //Add Adressen
        if(dto.getAdressen() != null){
            for (AdresseDTO adresse : dto.getAdressen()){
                adresseService.addUnternehmenToAdresse(adresse, unternehmen);
            }
        }
    }
}
