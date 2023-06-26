package org.hbrs.se2.project.aldavia.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class StellenanzeigenService {

    private final StellenanzeigeRepository stellenanzeigenRepository;
    private final TaetigkeitsfeldService taetigkeitsfeldService;

    private final Logger logger = LoggerFactory.getLogger(StellenanzeigenService.class);


    public Stellenanzeige getStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) throws StellenanzeigenException {
        try {
            logger.info("Search for Stellenanzeige: " + stellenanzeigeDTO);
            return stellenanzeigenRepository.findById(stellenanzeigeDTO.getId()).orElseThrow();
        }
        catch (Exception e) {
            logger.error("Stellenanzeige not found: " + stellenanzeigeDTO);
            throw new StellenanzeigenException("Stellenanzeige not found", StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND);
        }
    }

    public List<Stellenanzeige> getStellenanzeigen() {
        return stellenanzeigenRepository.findAll();
    }

    /**
     * Creates a new Stellenanzeige
     * @param dto StellenanzeigeDTO
     * @throws ProfileException if the Stellenanzeige could not be created
     */
    public void addStellenanzeige(StellenanzeigeDTO dto, Unternehmen unternehmen) throws ProfileException {
        logger.info("Creating new Stellenanzeige: " + dto);
        Stellenanzeige stellenanzeige = stellenanzeigenRepository.save(Stellenanzeige.builder()
                .bezeichnung(dto.getBezeichnung())
                .beschreibung(dto.getBeschreibung())
                .bezahlung(dto.getBezahlung())
                .beschaeftigungsverhaeltnis(dto.getBeschaeftigungsverhaeltnis())
                .erstellungsdatum(dto.getErstellungsdatum())
                .ende(dto.getEnde())
                .start(dto.getStart())
                .unternehmen_stellenanzeigen(unternehmen)
                .build());
        logger.info("Adding Taetigkeitsfelder to Stellenanzeige: " + dto);
        for (TaetigkeitsfeldDTO t : dto.getTaetigkeitsfelder()) {
            logger.info("Adding Taetigkeitsfeld: " + t);
            stellenanzeige = taetigkeitsfeldService.addTaetigkeitsfeldToStellenanzeige(t, stellenanzeige);
        }
        logger.info("Stellenanzeige created: " + stellenanzeige);
        stellenanzeigenRepository.save(stellenanzeige);
    }

    /**
     * Deletes a Stellenanzeige
     * @param stellenanzeige Stellenanzeige
     */
    @SneakyThrows
    public void deleteStellenanzeige(Stellenanzeige stellenanzeige) {
        logger.info("Deleting Stellenanzeige: " + stellenanzeige);

        try{
            logger.info("Deleting Taetigkeitsfelder from Stellenanzeige: " + stellenanzeige);
            List<Taetigkeitsfeld> toRemoveTaetigkeitsfelder = new ArrayList<>();
            for (Taetigkeitsfeld t : stellenanzeige.getTaetigkeitsfelder()) {
                logger.info("Adding Taetigkeitsfeld to toRemoveTaetigkeitsfelder");
                toRemoveTaetigkeitsfelder.add(t);
            }
            for (Taetigkeitsfeld t : toRemoveTaetigkeitsfelder){
                logger.info("Removing Taetigkeitsfeld: " + t);
                taetigkeitsfeldService.deleteTaetigkeitsfeldFromStellenanzeige(t, stellenanzeige);
            }
            logger.info("Removing Unternehmen from Stellenanzeige: " + stellenanzeige);
            Unternehmen unternehmen = stellenanzeige.getUnternehmen_stellenanzeigen();
            unternehmen.removeStellenanzeige(stellenanzeige);
            logger.info("Stellenanzeige removed from Unternehmen: " + unternehmen);
            stellenanzeigenRepository.delete(stellenanzeige);
            logger.info("Stellenanzeige deleted: " + stellenanzeige);
        } catch (Exception e) {
            logger.error("An error occurred while deleting Stellenanzeige: " + stellenanzeige + " " + Arrays.toString(e.getStackTrace()));
            throw new StellenanzeigenException("Stellenanzeige could not be deleted. Here Stacktrace: " + Arrays.toString(e.getStackTrace()), StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_COULD_NOT_BE_DELETED);

        }

    }
}
