package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class TaetigkeitsfeldService {
    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    private final Logger logger = LoggerFactory.getLogger(TaetigkeitsfeldService.class);

    public Taetigkeitsfeld getTaetigkeitsfeld(TaetigkeitsfeldDTO taetigkeitsfeldDTO){
        logger.info("Fetching Taetigkeitsfeld " + taetigkeitsfeldDTO.getName() + " from DB");
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        return awaitTaetigkeitsfeld.orElse(taetigkeitsfeldRepository.save(Taetigkeitsfeld.builder()
                .bezeichnung(taetigkeitsfeldDTO.getName())
                .build()));
    }

    /**
     * Add a Taetigkeitsfeld to a student
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @return Taetigkeitsfeld
     */
    public void addStudentToTaetigkeitsfeld (TaetigkeitsfeldDTO taetigkeitsfeldDTO, Student student) {
        logger.info("Add student " + student.getUser().getUserid() + " to " + taetigkeitsfeldDTO.getName());
        Taetigkeitsfeld taetigkeitsfeld = getTaetigkeitsfeld(taetigkeitsfeldDTO);
        taetigkeitsfeld = taetigkeitsfeld.addStudent(student);
        taetigkeitsfeldRepository.save(taetigkeitsfeld);
    }

    /**
     * Remove a student from a Taetigkeitsfeld and save it
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @param student The student
     */
    public Taetigkeitsfeld removeStudentFromTaetigkeitsfeld(TaetigkeitsfeldDTO taetigkeitsfeldDTO, Student student) throws PersistenceException {
        logger.info("Remove student " + student.getUser().getUserid() + " to " + taetigkeitsfeldDTO.getName());
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        if (awaitTaetigkeitsfeld.isPresent()){
            Taetigkeitsfeld taetigkeitsfeld = awaitTaetigkeitsfeld.get();
            taetigkeitsfeld = taetigkeitsfeld.removeStudent(student);
            return taetigkeitsfeldRepository.save(taetigkeitsfeld);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.TAETIGKEITSFELD_NOT_FOUND, "Taetigkeitsfeld not found");
        }
    }

    /**
     * Add a Taetigkeitsfeld to a Stellenanzeige
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @param stellenanzeige The Stellenanzeige
     * @return Stellenanzeige with the added Taetigkeitsfeld
     */
    public Stellenanzeige addTaetigkeitsfeldToStellenanzeige(TaetigkeitsfeldDTO taetigkeitsfeldDTO, Stellenanzeige stellenanzeige) {
        logger.info("Add Stellenanzeige " + stellenanzeige.getId() + " to " + taetigkeitsfeldDTO.getName());
        Taetigkeitsfeld taetigkeitsfeld = getTaetigkeitsfeld(taetigkeitsfeldDTO);
        taetigkeitsfeld.addStellenanzeige(stellenanzeige);
        taetigkeitsfeldRepository.save(taetigkeitsfeld);
        return stellenanzeige;
    }

    /**
     * Delete a Taetigkeitsfeld from a Stellenanzeige
     * @param taetigkeitsfeld The Taetigkeitsfeld
     * @param stellenanzeige The Stellenanzeige
     * @return Stellenanzeige with the deleted Taetigkeitsfeld
     */
    public Stellenanzeige deleteTaetigkeitsfeldFromStellenanzeige(Taetigkeitsfeld taetigkeitsfeld, Stellenanzeige stellenanzeige){
        logger.info("Remove Stellenanzeige " + stellenanzeige.getId() + " from " + taetigkeitsfeld.getBezeichnung());
        taetigkeitsfeld.removeStellenanzeige(stellenanzeige);
        logger.info("REMOVE STELLENANZEIGE WAS SUCCESSFUL: " + taetigkeitsfeld.getBezeichnung() + "FROM " + stellenanzeige.getId() + ". Size of Stellenanzeige: " + taetigkeitsfeld.getStellenanzeigen().size());
        taetigkeitsfeldRepository.save(taetigkeitsfeld);
        logger.info("SAVE WAS SUCCESSFUL");
        return stellenanzeige;
    }
}
