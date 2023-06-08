package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.BewerbungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class BewerbungsService {

    @Autowired
    private BewerbungRepository bewerbungRepository;

    /**
     * Add a Bewerbung
     * @param student The student
     * @param stellenanzeige The stellenanzeige
     */
    public Bewerbung addBewerbung(Student student, Stellenanzeige stellenanzeige) throws BewerbungsException {
        if (bewerbungRepository.findByStudentAndStellenanzeige(student, stellenanzeige).isPresent()) {
            throw new BewerbungsException("Bewerbung already exists", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_ALREADY_EXISTS);
        }
        Bewerbung bewerbung = Bewerbung.builder()
                .student(student)
                .stellenanzeige(stellenanzeige)
                .build();
        return bewerbungRepository.save(bewerbung);
    }

    /**
     * Remove a Bewerbung
     * @param bewerbung The bewerbung
     * @throws BewerbungsException if bewerbung not found
     */
    public void removeBewerbung(Bewerbung bewerbung) throws BewerbungsException {
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
            return bewerbungRepository.findById(bewerbungsDTO.getId()).orElseThrow();
        }
        catch (Exception e) {
            throw new BewerbungsException("Bewerbung not found", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_NOT_FOUND);
        }
    }

    /**
     * Get all Bewerbungen of a student
     * @param student The student
     * @return List of Bewerbungen
     */
    public List<Bewerbung> getBewerbungen(Student student) {
        return bewerbungRepository.findByStudent(student);
    }

    /**
     * Get all Bewerbungen of a Stellenanzeige
     * @param stellenanzeige The stellenanzeige
     * @return List of Bewerbungen
     */
    public List<Bewerbung> getBewerbungen(Stellenanzeige stellenanzeige) {
        return bewerbungRepository.findByStellenanzeige(stellenanzeige);
    }
}
