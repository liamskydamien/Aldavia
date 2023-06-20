package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class BewerbungsOverviewStudent {
    private final StudentService studentService;
    private final BewerbungsService bewerbungsService;

    /**
     * Get the Bewerbungen of a student
     * There are a few security issues here, but we don't have time to fix them now :(
     * You can access the Bewerbungen of other students through the DTO
     * @param username The username of the student
     * @return List of BewerbungsDTOs
     * @throws ProfileException if student not found
     */
    public List<BewerbungsDTO> getBewerbungenStudent(String username) throws ProfileException {
        Student student = studentService.getStudent(username);
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        return bewerbungsListFactory.createBewerbungsDTOs(student.getBewerbungen());
    }

    /**
     * Remove a Bewerbung
     * @param bewerbung The bewerbung
     */
    public void removeBewerbung(BewerbungsDTO bewerbung) throws BewerbungsException {
        bewerbungsService.removeBewerbung(bewerbung);
    }
}
