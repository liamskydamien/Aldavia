package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
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
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        Taetigkeitsfeld taetigkeitsfeld;
        if (awaitTaetigkeitsfeld.isPresent()){
            taetigkeitsfeld = awaitTaetigkeitsfeld.get();
        }
        else {
            taetigkeitsfeld = new Taetigkeitsfeld();
            taetigkeitsfeld.setBezeichnung(taetigkeitsfeldDTO.getName());
        }
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
}
