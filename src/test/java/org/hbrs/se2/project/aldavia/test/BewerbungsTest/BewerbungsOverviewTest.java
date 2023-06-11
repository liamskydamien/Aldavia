package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewStudent;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class BewerbungsOverviewTest {
    private BewerbungsOverviewStudent bewerbungsOverviewStudent;
    @Mock
    private StudentService studentServiceMock;

    @BeforeEach
    public void setup() {
        bewerbungsOverviewStudent = new BewerbungsOverviewStudent(studentServiceMock);
    }

    @Test
    public void testGetBewerbungen() throws ProfileException {

        // Setup
        String userid = "testBewerbung";

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .beschreibung("test")
                .build();

        Student student = Student.builder()
                .user(null)
                .matrikelNummer("123456")
                .id(1)
                .studiengang("test")
                .vorname("test")
                .nachname("test")
                .build();

        Bewerbung bewerbung = Bewerbung.builder()
                .id(1)
                .student(null)
                .datum(null)
                .status(null)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben("Test")
                .build();

        Bewerbung bewerbung2 = Bewerbung.builder()
                .id(2)
                .student(null)
                .datum(null)
                .status(null)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben("Test")
                .build();

        List<Bewerbung> bewerbungen = new ArrayList<>();
        bewerbungen.add(bewerbung);
        bewerbungen.add(bewerbung2);
        student.setBewerbungen(bewerbungen);

        given(studentServiceMock.getStudent(userid)).willReturn(student);

       // Run the test
        List<BewerbungsDTO> result = bewerbungsOverviewStudent.getBewerbungen(userid);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }
}
