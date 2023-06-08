package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StellenanzeigenService {

    @Autowired
    private StellenanzeigeRepository stellenanzeigenRepository;

    public Stellenanzeige getStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO){
        try {
            return stellenanzeigenRepository.findById(stellenanzeigeDTO.getId()).orElseThrow();
        }
        catch (Exception e) {
            //TODO Add customexception
            throw new RuntimeException("Stellenanzeige not found");
        }
    }
}
