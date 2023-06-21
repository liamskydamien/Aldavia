package org.hbrs.se2.project.aldavia.service;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class StellenanzeigenService {

    private final StellenanzeigeRepository stellenanzeigenRepository;
    private final TaetigkeitsfeldService taetigkeitsfeldService;
    private final UnternehmenService unternehmenService;


    public Stellenanzeige getStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) throws StellenanzeigenException {
        try {
            return stellenanzeigenRepository.findById(stellenanzeigeDTO.getId()).orElseThrow();
        }
        catch (Exception e) {
            throw new StellenanzeigenException("Stellenanzeige not found", StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND);
        }
    }

    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigenRepository.findAll();
    }
    public void updateStellenanzeige(StellenanzeigeDTO dto) throws StellenanzeigenException, ProfileException {
        /*try {

            if (dto.getBewerbungen() != null) {
                if (!(dto.getBewerbungen().equals(stellenanzeige.getBewerbungen()))) {
                    for (Bewerbung b : stellenanzeige.getBewerbungen()) {
                        stellenanzeige.removeBewerbung(b);
                    }
                    for (BewerbungsDTO b : dto.getBewerbungen()) {
                        stellenanzeige.addBewerbung(bewerbungsService.getBewerbung(b));
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
                    for (TaetigkeitsfeldDTO t : dto.getTaetigkeitsfelder()) {
                        stellenanzeige.addTaetigkeitsfeld(taetigkeitsfeldService.getTaetigkeitsfeld(t));
                        taetigkeitsfeldRepository.save(taetigkeitsfeldService.getTaetigkeitsfeld(t));
                    }
                }

            }

            if(dto.getUnternehmen() != null) {
                if (stellenanzeige.getUnternehmen_stellenanzeigen() != null) {
                    stellenanzeige.setUnternehmen(unternehmenService.getUnternehmen(dto.getUnternehmen().getUsername()));
                    unternehmenRepository.save(unternehmenService.getUnternehmen(dto.getUnternehmen().getUsername()));
                }
            }


            if(dto.getStart() != null) {
                stellenanzeige.setStart(dto.getStart());
            }

        } catch (Exception e) {
            throw new ProfileException("Error while updating Stellenazeige information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }*/

        Stellenanzeige stellenanzeige = getStellenanzeige(dto);
        stellenanzeige.setBezahlung(dto.getBezahlung());
        stellenanzeige.setBeschaeftigungsverhaeltnis(dto.getBeschaeftigungsverhaeltnis());
        stellenanzeige.setBeschreibung(dto.getBeschreibung());
        stellenanzeige.setBezeichnung(dto.getBezeichnung());
        stellenanzeige.setEnde(dto.getEnde());
        stellenanzeige.setErstellungsdatum(dto.getErstellungsdatum());
        stellenanzeige.setStart(dto.getStart());

        // Deletes and adds the Taeitgkeitsfelder
        for (Taetigkeitsfeld t : stellenanzeige.getTaetigkeitsfelder()) {
            taetigkeitsfeldService.deleteTaetigkeitsfeldFromStellenanzeige(t, stellenanzeige);
        }
        for (TaetigkeitsfeldDTO t : dto.getTaetigkeitsfelder()) {
            taetigkeitsfeldService.addTaetigkeitsfeldToStellenanzeige(t, stellenanzeige);
        }

        stellenanzeigenRepository.save(stellenanzeige);
    }

    /**
     * Creates a new Stellenanzeige
     * @param dto StellenanzeigeDTO
     * @throws ProfileException if the Stellenanzeige could not be created
     */
    public void addStellenanzeige(StellenanzeigeDTO dto) throws ProfileException {
        Stellenanzeige stellenanzeige = stellenanzeigenRepository.save(Stellenanzeige.builder()
                .bezeichnung(dto.getBezeichnung())
                .beschreibung(dto.getBeschreibung())
                .bezahlung(dto.getBezahlung())
                .beschaeftigungsverhaeltnis(dto.getBeschaeftigungsverhaeltnis())
                .erstellungsdatum(dto.getErstellungsdatum())
                .ende(dto.getEnde())
                .start(dto.getStart())
                .unternehmen_stellenanzeigen(unternehmenService.getUnternehmen(dto.getUnternehmen().getUsername()))
                .build());
        for (TaetigkeitsfeldDTO t : dto.getTaetigkeitsfelder()) {
            taetigkeitsfeldService.addTaetigkeitsfeldToStellenanzeige(t, stellenanzeige);
        }
        stellenanzeigenRepository.save(stellenanzeige);
    }
}
