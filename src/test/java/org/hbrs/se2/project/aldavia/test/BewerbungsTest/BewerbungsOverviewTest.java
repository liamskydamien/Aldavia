package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewStudent;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
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
    public static final String TEST = "test";
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

        User user = User.builder()
                .userid(userid)
                .build();

        Unternehmen unternehmen = Unternehmen.builder()
                .id(1)
                .name(TEST)
                .user(user)
                .build();

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .beschreibung(TEST)
                .unternehmen_stellenanzeigen(unternehmen)
                .bezeichnung(TEST)
                .taetigkeitsfelder(new ArrayList<>())
                .start(null)
                .ende(null)
                .erstellungsdatum(null)
                .bezahlung(TEST)
                .beschaeftigungsverhaeltnis(TEST)
                .bezeichnung(TEST)
                .build();


        Student student = Student.builder()
                .user(user)
                .matrikelNummer("123456")
                .id(1)
                .studiengang(TEST)
                .vorname(TEST)
                .nachname(TEST)
                .build();

        Bewerbung bewerbung = Bewerbung.builder()
                .id(1)
                .student(student)
                .datum(null)
                .status(null)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(TEST)
                .build();

        Bewerbung bewerbung2 = Bewerbung.builder()
                .id(2)
                .student(null)
                .datum(null)
                .status(null)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(TEST)
                .build();

        List<Bewerbung> bewerbungen = new ArrayList<>();
        bewerbungen.add(bewerbung);
        bewerbungen.add(bewerbung2);
        student.setBewerbungen(bewerbungen);

        given(studentServiceMock.getStudent(userid)).willReturn(student);

       // Run the test
        List<BewerbungsDTO> result = bewerbungsOverviewStudent.getBewerbungenStudent(userid);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }
}
