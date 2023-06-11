package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BewerbungsListFactoryTest {
    public static final String TEST = "test";
    private final LocalDate datum = LocalDate.now();

    private List<Bewerbung> bewerbungsList;
    private Student student;

    private Unternehmen unternehmen;
    private StellenanzeigeDTO stellenanzeigeDTO;

    @BeforeEach
    public void setup(){
        User user =  User.builder()
                .userid(TEST)
                .build();

        student = Student.builder()
                .id(1)
                .vorname(TEST)
                .nachname(TEST)
                .user(user)
                .build();

        unternehmen = Unternehmen.builder()
                .id(1)
                .name(TEST)
                .user(user)
                .build();

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .taetigkeitsfelder(new ArrayList<>())
                .unternehmen_stellenanzeigen(unternehmen)
                .beschreibung(TEST)
                .start(datum)
                .ende(datum)
                .erstellungsdatum(datum)
                .bezahlung(TEST)
                .bezeichnung(TEST)
                .beschaeftigungsverhaeltnis(TEST)
                .beschaeftigungsumfang(TEST)
                .build();

        stellenanzeigeDTO = StellenanzeigeDTOFactory.getInstance().createStellenanzeigeDTO(stellenanzeige);

        Bewerbung bewerbung1 = Bewerbung.builder()
                .id(1)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(TEST)
                .datum(LocalDate.now())
                .build();

        Bewerbung bewerbung2 = Bewerbung.builder()
                .id(1)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(TEST)
                .datum(LocalDate.now())
                .build();

        bewerbungsList = new ArrayList<>();
        bewerbungsList.add(bewerbung1);
        bewerbungsList.add(bewerbung2);
    }

    @Test
    public void testCreateBewerbungsListStudent() {
        // Execute
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        assertEquals(bewerbungsListFactory, BewerbungsListFactory.getInstance());

        List<BewerbungsDataDTO> actualCreateBewerbungsListResult = bewerbungsListFactory.createBewerbungsDataStudentDTOs(bewerbungsList);
        for (BewerbungsDataDTO bewerbungsDataDTO : actualCreateBewerbungsListResult) {
            assertEquals(1, bewerbungsDataDTO.getId());
            assertEquals(student.getVorname(), bewerbungsDataDTO.getStudent().getVorname());
            assertEquals(student.getNachname(), bewerbungsDataDTO.getStudent().getNachname());
            assertEquals("student/test", bewerbungsDataDTO.getStudent().getProfileLink());
            assertEquals(stellenanzeigeDTO, bewerbungsDataDTO.getStellenanzeige());
            assertEquals(TEST, bewerbungsDataDTO.getBewerbungsSchreiben());
        }
    }

    @Test
    public void testCreateBewerbungsListUnternhemen() {
        // Execute
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        assertEquals(bewerbungsListFactory, BewerbungsListFactory.getInstance());

        List<BewerbungsDataDTO> actualCreateBewerbungsListResult = bewerbungsListFactory.createBewerbungsDataUnternehmenDTOs(bewerbungsList);
        for (BewerbungsDataDTO bewerbungsDataDTO : actualCreateBewerbungsListResult) {
            assertEquals(1, bewerbungsDataDTO.getId());
            assertEquals(unternehmen.getName(), bewerbungsDataDTO.getUnternehmen().getName());
            assertEquals("unternehmen/test", bewerbungsDataDTO.getStudent().getProfileLink());
            assertEquals(stellenanzeigeDTO, bewerbungsDataDTO.getStellenanzeige());
            assertEquals(TEST, bewerbungsDataDTO.getBewerbungsSchreiben());
        }
    }
}
