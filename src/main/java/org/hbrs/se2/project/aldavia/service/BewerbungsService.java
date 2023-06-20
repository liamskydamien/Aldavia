package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.BewerbungRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BewerbungsService {

    @Autowired
    private BewerbungRepository bewerbungRepository;

    private final Logger logger = LoggerFactory.getLogger(BewerbungsService.class);

    /**
     * Add a Bewerbung
     * @param student The student
     * @param stellenanzeige The stellenanzeige
     */
    public Bewerbung addBewerbung(Student student, Stellenanzeige stellenanzeige, String bewerbungsSchreiben) throws BewerbungsException {
        logger.info("Check if Bewerbung already exists");
        if (bewerbungRepository.findByStudentAndStellenanzeige(student, stellenanzeige).isPresent()) {
            throw new BewerbungsException("Bewerbung already exists", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_ALREADY_EXISTS);
        }
        logger.info("Creating Bewerbung " + student.getVorname() + " " + student.getNachname() + " for Stellenanzeige " + stellenanzeige.getId());
        Bewerbung bewerbung = Bewerbung.builder()
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(bewerbungsSchreiben)
                .build();
        logger.info("Saving Bewerbung");
        return bewerbungRepository.save(bewerbung);
    }

    /**
     * Remove a Bewerbung
     * @param bewerbung The bewerbung
     * @throws BewerbungsException if bewerbung not found
     */
    public void removeBewerbung(Bewerbung bewerbung) throws BewerbungsException {
        logger.info("Deleting Bewerbung " + bewerbung.getId());
        bewerbungRepository.delete(bewerbung);
    }

    /**
     * Get a Bewerbung
     * @param bewerbungsDTO The bewerbungsDTO
     * @return Bewerbung
     * @throws BewerbungsException if bewerbung not found
     */
    public Bewerbung getBewerbung(BewerbungsDTO bewerbungsDTO) throws BewerbungsException {
        try {
            logger.info("Getting Bewerbung " + bewerbungsDTO.getId());
            return bewerbungRepository.findById(bewerbungsDTO.getId()).orElseThrow();
        }
        catch (Exception e) {
            throw new BewerbungsException("Bewerbung not found", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_NOT_FOUND);
        }
    }

    /**
     * Remove a Bewerbung
     * @param bewerbung The bewerbung
     */
    public void removeBewerbung(BewerbungsDTO bewerbung) throws BewerbungsException {
        Bewerbung bewerbungEntity = getBewerbung(bewerbung);
        removeBewerbung(bewerbungEntity);
    }
}
