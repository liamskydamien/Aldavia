package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
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

    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

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
        return stellenanzeigenRepository.findAll();
    }
    public void updateStellenanzeigeInformationen(Stellenanzeige stellenanzeige, org.hbrs.se2.project.aldavia.dtos.impl.StellenanzeigeDTO dto) throws ProfileException {
        try {

            if (dto.getBewerbungen() != null) {
                if (!(dto.getBewerbungen().equals(stellenanzeige.getBewerbungen()))) {
                    for (Bewerbung b : stellenanzeige.getBewerbungen()) {
                        stellenanzeige.removeBewerbung(b);
                    }
                    for (Bewerbung b : dto.getBewerbungen()) {
                        stellenanzeige.addBewerbung(b);
                    }
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

            if(dto.getTaetigkeitsfelder() != null) {
                if (!(dto.getTaetigkeitsfelder().equals(stellenanzeige.getTaetigkeitsfelder()))) {
                    for (Taetigkeitsfeld t : stellenanzeige.getTaetigkeitsfelder()) {
                        stellenanzeige.removeTaetigkeitsfeld(t);
                        taetigkeitsfeldRepository.save(t);
                    }
                    for (Taetigkeitsfeld t : dto.getTaetigkeitsfelder()) {
                        stellenanzeige.addTaetigkeitsfeld(t);
                        taetigkeitsfeldRepository.save(t);
                    }
                }

            }

            if(dto.getUnternehmen() != null) {
                if (stellenanzeige.getUnternehmen_stellenanzeigen() != null) {
                    stellenanzeige.setUnternehmen(dto.getUnternehmen());
                    unternehmenRepository.save(dto.getUnternehmen());
                }
            }


            if(dto.getStart() != null) {
                stellenanzeige.setStart(dto.getStart());
            }

        } catch (Exception e) {
            throw new ProfileException("Error while updating Stellenazeige information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

    public void newStellenanzeige(org.hbrs.se2.project.aldavia.dtos.impl.StellenanzeigeDTO dto) throws ProfileException {
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        updateStellenanzeigeInformationen(stellenanzeige,dto);

    }
}
