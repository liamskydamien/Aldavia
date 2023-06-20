package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StudentProfileDTOFactoryTest {

    private StudentProfileDTOFactory studentProfileDTOFactory = StudentProfileDTOFactory.getInstance();
    @Test
    public void testEmpty() throws ProfileException {
        Student student = Student.builder()
                .user(User.builder()
                        .userid("username")
                        .password("password")
                        .email("email")
                        .build())
                .build();
        StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student);
        assertEquals(studentProfileDTO.getVorname(), "");
        assertEquals(studentProfileDTO.getNachname(), "");
        assertEquals(studentProfileDTO.getGeburtsdatum().getDayOfYear(), LocalDate.now().getDayOfYear());
        assertEquals(studentProfileDTO.getGeburtsdatum().getMonthValue(), LocalDate.now().getMonthValue());
        assertEquals(studentProfileDTO.getGeburtsdatum().getYear(), LocalDate.now().getYear());
        assertEquals(studentProfileDTO.getTelefonnummer(), "");
        assertEquals(studentProfileDTO.getLebenslauf(), "");
        assertEquals(studentProfileDTO.getStudiengang(), "");
        assertEquals(studentProfileDTO.getStudienbeginn().getDayOfYear(), LocalDate.now().getDayOfYear());
        assertEquals(studentProfileDTO.getStudienbeginn().getMonthValue(), LocalDate.now().getMonthValue());
        assertEquals(studentProfileDTO.getStudienbeginn().getYear(), LocalDate.now().getYear());
        assertEquals(studentProfileDTO.getBeschreibung(), "");
        assertEquals(studentProfileDTO.getUsername(), "username");

    }

    @Test
    public void testNegative(){
        Student student = new Student();
        assertThrows(ProfileException.class, () -> studentProfileDTOFactory.createStudentProfileDTO(student));
    }
}
