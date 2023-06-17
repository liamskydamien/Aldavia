package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigenDataDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigenDataDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class BewerbungsOverviewUnternehmen {

    private final UnternehmenService unternehmenService;
    private final StellenanzeigenDataDTOFactory stellenanzeigenDataDTOFactory = StellenanzeigenDataDTOFactory.getInstance();

    /**
     * Get the Bewerbungen for a Stellenanzeige
     * @param username The username of the Unternehmen
     * @return List of StellenanzeigenDataDTOs containing the Bewerbungen and the Stellenanzeige
     * @throws ProfileException if Unternehmen not found
     */
    public List<StellenanzeigenDataDTO> getBewerbungenStellenanzeige(String username) throws ProfileException {
        Unternehmen unternehmen = unternehmenService.getUnternehmen(username);
        Set<Stellenanzeige> stellenanzeigen = unternehmen.getStellenanzeigen();
        return stellenanzeigen.stream().map(stellenanzeigenDataDTOFactory::createStellenanzeigenDataDTO).collect(Collectors.toList());
    }
}
