package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Transactional
public class BewerbungsOverviewStudent {
    private StudentService studentService;
    private BewerbungsService bewerbungsService;

    @Autowired
    public BewerbungsOverviewStudent(StudentService studentService, BewerbungsService bewerbungsService) {
        this.studentService = studentService;
        this.bewerbungsService = bewerbungsService;
    }

    /**
     * Get the Bewerbungen of a student
     * @param username The username of the student
     * @return List of BewerbungsDTOs
     * @throws ProfileException if student not found
     */
    public List<BewerbungsDataDTO> getBewerbungenStudent(String username) throws ProfileException {
        Student student = studentService.getStudent(username);
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        return bewerbungsListFactory.createBewerbungsDataStudentDTOs(student.getBewerbungen());
    }
}
