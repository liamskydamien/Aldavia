package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class BewerbungsOverviewStudent {
    private StudentService studentService;

    /**
     * Get the Bewerbungen of a student
     * @param username The username of the student
     * @return List of BewerbungsDTOs
     * @throws ProfileException if student not found
     */
    public List<BewerbungsDTO> getBewerbungen(String username) throws ProfileException {
        Student student = studentService.getStudent(username);
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        return bewerbungsListFactory.createBewerbungsDTOs(student.getBewerbungen());
    }
}
