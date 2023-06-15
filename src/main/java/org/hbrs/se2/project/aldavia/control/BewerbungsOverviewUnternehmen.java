package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class BewerbungsOverviewUnternehmen {

    private StellenanzeigenService stellenanzeigenService;

    /**
     * Get the Bewerbungen of a Stellenanzeige
     * @param stellenanzeigeDTO The StellenanzeigeDTO of the Stellenanzeige
     * @return List of BewerbungsDTOs
     */
    public List<BewerbungsDataDTO> getBewerbungen(StellenanzeigeDTO stellenanzeigeDTO) {
        Stellenanzeige stellenanzeige = stellenanzeigenService.getStellenanzeige(stellenanzeigeDTO);
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        return bewerbungsListFactory.createBewerbungsDataUnternehmenDTOs(stellenanzeige.getBewerbungen());
    }
}
