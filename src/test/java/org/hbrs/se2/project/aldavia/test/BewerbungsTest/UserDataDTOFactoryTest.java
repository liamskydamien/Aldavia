package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.factories.UserDataDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StudentDataDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenDataDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDataDTOFactoryTest {

    public static final String TEST = "test";
    private final User user = User.builder()
            .userid(TEST)
            .build();

    @Test
    public void testSingleton(){
        UserDataDTOFactory factory1 = UserDataDTOFactory.getInstance();
        assertEquals(factory1, UserDataDTOFactory.getInstance());
    }

    @Test
    public void testCreateUserDataDTOStudent(){
        // Setup
        Student student = Student.builder()
                .id(1)
                .vorname(TEST)
                .nachname(TEST)
                .user(user)
                .build();

        UserDataDTOFactory factory = UserDataDTOFactory.getInstance();
        StudentDataDTO studentDataDTO = factory.createStudentDataDTO(student);
        assertEquals(studentDataDTO.getVorname(), student.getVorname());
        assertEquals(studentDataDTO.getNachname(), student.getNachname());
        assertEquals("/student/test", studentDataDTO.getProfileLink());
    }

    @Test
    public void testCreateUserDataDTOUnternehmen(){
        // Setup
        Unternehmen unternehmen = Unternehmen.builder()
                .id(1)
                .name(TEST)
                .user(user)
                .build();

        UserDataDTOFactory factory = UserDataDTOFactory.getInstance();
        UnternehmenDataDTO unternehmenDataDTO = factory.createUnternehmenDataDTO(unternehmen);
        assertEquals(unternehmenDataDTO.getName(), unternehmen.getName());
        assertEquals("/unternehmen/test", unternehmenDataDTO.getProfileLink());
    }
}
