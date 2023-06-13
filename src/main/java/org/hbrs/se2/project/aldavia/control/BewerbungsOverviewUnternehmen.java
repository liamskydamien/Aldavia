package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigenDataDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
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

@Component
@Transactional
@RequiredArgsConstructor
public class BewerbungsOverviewUnternehmen {

    private final UnternehmenService unternehmenService;
    private final StellenanzeigenDataDTOFactory stellenanzeigenDataDTOFactory = StellenanzeigenDataDTOFactory.getInstance();

    /**
     * Gibt alle Bewerbungen zu einer Stellenanzeige zurück
     * @param username Der Username des Unternehmens
     * @return Liste von Bewerbungen
     * @throws ProfileException wenn Bewerbungen nicht gefunden werden konnten
     */
    public List<StellenanzeigenDataDTO> getBewerbungen(String username) throws ProfileException {
        Unternehmen unternehmen =  unternehmenService.getUnternehmen(username);
        Set<Stellenanzeige> stellenanzeigen = unternehmen.getStellenanzeigen();
        List<StellenanzeigenDataDTO> stellenanzeigenDataDTOList = new ArrayList<>();
        for (Stellenanzeige stellenanzeige : stellenanzeigen) {
            stellenanzeigenDataDTOList.add(stellenanzeigenDataDTOFactory.createStellenanzeigenDataDTO(stellenanzeige));
        }
        return stellenanzeigenDataDTOList;
    }
}
