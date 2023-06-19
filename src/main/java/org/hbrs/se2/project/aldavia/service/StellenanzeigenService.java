package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class StellenanzeigenService {

    @Autowired
    private StellenanzeigeRepository stellenanzeigenRepository;

    public Stellenanzeige getStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) throws StellenanzeigenException {
        try {
            return stellenanzeigenRepository.findById(stellenanzeigeDTO.getId()).orElseThrow();
        }
        catch (Exception e) {
            throw new StellenanzeigenException("Stellenanzeige not found", StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND);
        }
    }

    //TODO Add method to return all Stellenanzeigen
    public List<Stellenanzeige> getStellenanzeigen() {
        return new ArrayList<>();
    }
}
