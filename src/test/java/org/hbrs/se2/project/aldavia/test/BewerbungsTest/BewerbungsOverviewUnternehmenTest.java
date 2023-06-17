package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewUnternehmen;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigenDataDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class BewerbungsOverviewUnternehmenTest {

    private static final String TEST = "testvalue";
    @Mock
    private UnternehmenService unternehmenServiceMock;

    private BewerbungsOverviewUnternehmen bewerbungsOverviewUnternehmen;

    @BeforeEach
    public void setup() {
        bewerbungsOverviewUnternehmen = new BewerbungsOverviewUnternehmen(unternehmenServiceMock);
    }

    @Test
    public void testGetBewerbungenStellenanzeige() throws ProfileException {
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
        stellenanzeige.addBewerbung(bewerbung);
        stellenanzeige.addBewerbung(bewerbung2);
        unternehmen.addStellenanzeige(stellenanzeige);

        given(unternehmenServiceMock.getUnternehmen(userid)).willReturn(unternehmen);

        // Run the test
        final List<StellenanzeigenDataDTO> result = bewerbungsOverviewUnternehmen.getBewerbungenStellenanzeige(userid);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getBewerbungen().size());
        assertEquals(TEST, result.get(0).getBewerbungen().get(0).getBewerbungsSchreiben());
        assertEquals(TEST, result.get(0).getBewerbungen().get(1).getBewerbungsSchreiben());
    }

}
