package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StellenanzeigeService {

    @Autowired
    StellenanzeigeRepository stellenanzeigeRepository;

    @Autowired
    TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    UnternehmenRepository unternehmenRepository;

    @Autowired
    BewerbungsService bewerbungsService;

    @Autowired
    TaetigkeitsfeldService taetigkeitsfeldService;

    @Autowired
    UnternehmenService unternehmenService;

    public Stellenanzeige getStellenanzeigen(Integer id) throws ProfileException {
        Optional<Stellenanzeige> stellenanzeige = stellenanzeigeRepository.findById(id);
        if (stellenanzeige.isPresent()) {
            return stellenanzeige.get();
        } else {
            throw new ProfileException("Stellenanzeige not found", ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
        }
    }

    public List<Stellenanzeige> getAllStellenanzeigen() {
        return stellenanzeigeRepository.findAll();
    }

    public void updateStellenanzeigeInformationen(Stellenanzeige stellenanzeige, StellenanzeigeDTO dto) throws ProfileException {
        try {

            if (dto.getBewerbungen() != null && !(dto.getBewerbungen().equals(stellenanzeige.getBewerbungen()))) {
                    for (Bewerbung b : stellenanzeige.getBewerbungen()) {
                        stellenanzeige.removeBewerbung(b);
                    }
                    for (BewerbungsDTO b : dto.getBewerbungen()) {
                        stellenanzeige.addBewerbung(bewerbungsService.getBewerbung(b));
                    }
            }

            if(dto.getBeschreibung() != null) {
                stellenanzeige.setBeschreibung(dto.getBeschreibung());
            }

            if(dto.getBezeichnung() != null) {
                stellenanzeige.setBezeichnung(dto.getBezeichnung());
            }

            if(dto.getEnde() != null) {
                stellenanzeige.setEnde(dto.getEnde());
            }

            if(dto.getBezahlung() != null) {
                stellenanzeige.setBezahlung(dto.getBezahlung());
            }

            if(dto.getBeschaeftigungsverhaeltnis() != null) {
                stellenanzeige.setBeschaeftigungsverhaeltnis(dto.getBeschaeftigungsverhaeltnis());
            }

            if(dto.getErstellungsdatum() != null) {
                stellenanzeige.setErstellungsdatum(dto.getErstellungsdatum());
            }

            if(dto.getTaetigkeitsfelder() != null && !(dto.getTaetigkeitsfelder().equals(stellenanzeige.getTaetigkeitsfelder()))) {
                    for (Taetigkeitsfeld t : stellenanzeige.getTaetigkeitsfelder()) {
                        stellenanzeige.removeTaetigkeitsfeld(t);
                        taetigkeitsfeldRepository.save(t);
                    }
                    for (TaetigkeitsfeldDTO t : dto.getTaetigkeitsfelder()) {
                        stellenanzeige.addTaetigkeitsfeld(taetigkeitsfeldService.getTaetigkeitsfeld(t));
//                        taetigkeitsfeldRepository.save(t);
                    }
            }

            if(dto.getUnternehmen() != null & stellenanzeige.getUnternehmen_stellenanzeigen() != null) {
                   stellenanzeige.setUnternehmen(unternehmenService.getUnternehmen(dto.getUnternehmen().getName()));
//                   unternehmenRepository.save(dto.getUnternehmen());
            }


            if(dto.getStart() != null) {
                stellenanzeige.setStart(dto.getStart());
            }

        } catch (Exception e) {
            throw new ProfileException("Error while updating Stellenazeige information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

    public void deleteStellenanzeige(Stellenanzeige stellenanzeige) throws ProfileException {
        if (stellenanzeige.getTaetigkeitsfelder() != null) {
            for (Taetigkeitsfeld t : stellenanzeige.getTaetigkeitsfelder()) {
                stellenanzeige.removeTaetigkeitsfeld(t);
                taetigkeitsfeldRepository.save(t);
            }
        }

        if(stellenanzeige.getTaetigkeitsfelder() != null) {
            for (Taetigkeitsfeld t : stellenanzeige.getTaetigkeitsfelder()) {
                stellenanzeige.removeTaetigkeitsfeld(t);
                taetigkeitsfeldRepository.save(t);
            }
        }
        stellenanzeigeRepository.delete(stellenanzeige);
    }

    public void newStellenanzeige(StellenanzeigeDTO dto) throws ProfileException {
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        updateStellenanzeigeInformationen(stellenanzeige,dto);
    }

}

