package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.service.AdresseService;
import org.hbrs.se2.project.aldavia.service.StellenanzeigeService;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.UnternehmenProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnternehmenProfileControl {

    @Autowired
    UnternehmenService unternehmenService;


    private final UnternehmenProfileDTOFactory unternehmenProfileDTOFactory = UnternehmenProfileDTOFactory.getInstance();

    public UnternehmenProfileDTO getUnternehmenProfileDTO(String username) throws ProfileException {
        try {
            Unternehmen unternehmen = unternehmenService.getUnternehmen(username);
            UnternehmenProfileDTO dto = unternehmenProfileDTOFactory.createUnternehmenProfileDTO(unternehmen);
            return dto;
        } catch (Exception e) {
            throw new ProfileException("Error while loading Unternehmen Profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

    public void createAndUpdateUnternehmenProfile(UnternehmenProfileDTO dto, String username) throws ProfileException {
        Unternehmen unternehmen = unternehmenService.getUnternehmen(username);
        try {
            unternehmenService.updateUnternehmenInformation(unternehmen,dto);

        } catch(Exception e) {
            throw new ProfileException("Error while loading Unternehmen Profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }




}
