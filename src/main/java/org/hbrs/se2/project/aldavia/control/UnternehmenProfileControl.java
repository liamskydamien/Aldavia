package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.UnternehmenProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class UnternehmenProfileControl {
    private final UnternehmenService unternehmenService;
    private final UnternehmenProfileDTOFactory unternehmenProfileDTOFactory;
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

    public void createAndUpdateUnternehmenProfile(UnternehmenProfileDTO dto, String userName) throws ProfileException {
        logger.info("Getting Company from Database to update its information: " + userName);
        Unternehmen unternehmen = unternehmenService.getUnternehmen(userName);
        logger.info("Found a company to the userName with the following company name: " + unternehmen.getName());
        try {
            unternehmenService.updateUnternehmenInformation(unternehmen,dto);

        } catch(Exception e) {
            throw e;
        }
        logger.info("Sucessfully updatet the information of the company: " + unternehmen.getName());
    }





}
