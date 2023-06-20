package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class BewerbungsControl {

    private final BewerbungsService bewerbungsService;
    private final StudentService studentService;
    private final StellenanzeigenService stellenanzeigenService;

    Logger logger = LoggerFactory.getLogger(BewerbungsControl.class);

    /**
     * Adds a Bewerbung to the database
     * @param studentUsername The username of the student
     * @param stellenanzeigeDTO The DTO of the Stellenanzeige
     * @throws BewerbungsException if Bewerbung could not be added
     */
    public void addBewerbung(String studentUsername, StellenanzeigeDTO stellenanzeigeDTO, String bewerbungsSchreiben) throws BewerbungsException {
        try {
            logger.info("Retrieving Student and Stellenanzeige from Database");
            Student student = studentService.getStudent(studentUsername);
            Stellenanzeige stellenanzeige = stellenanzeigenService.getStellenanzeige(stellenanzeigeDTO);
            logger.info("Adding Bewerbung to Database");
            bewerbungsService.addBewerbung(student, stellenanzeige, bewerbungsSchreiben);
        }
        catch (Exception e) {
            logger.error("Bewerbung could not be added", e);
            throw new BewerbungsException("Bewerbung could not be added", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_COULD_NOT_BE_ADDED);
        }
    }

    /**
     * Deletes a Bewerbung from the database
     * @param bewerbungsDTO The DTO of the Bewerbung
     * @throws BewerbungsException if Bewerbung could not be deleted
     */
    public void deleteBewerbung(BewerbungsDTO bewerbungsDTO) throws BewerbungsException {
        try {
            logger.info("Deleting Bewerbung from Database");
            bewerbungsService.removeBewerbung(bewerbungsService.getBewerbung(bewerbungsDTO));
        } catch (Exception e) {
            logger.error("Bewerbung could not be deleted", e);
            throw new BewerbungsException("Bewerbung could not be deleted", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_COULD_NOT_BE_DELETED);
        }
    }

}
