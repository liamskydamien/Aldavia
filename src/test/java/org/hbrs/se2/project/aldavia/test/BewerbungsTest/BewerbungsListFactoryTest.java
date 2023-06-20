package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
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
    public void testCreateBewerbungsListStudent() throws ProfileException {
        // Execute
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        assertEquals(bewerbungsListFactory, BewerbungsListFactory.getInstance());

        List<BewerbungsDTO> actualCreateBewerbungsListResult = bewerbungsListFactory.createBewerbungsDTOs(bewerbungsList);
        for (BewerbungsDTO bewerbungsDataDTO : actualCreateBewerbungsListResult) {
            assertions(bewerbungsDataDTO, true);
        }
    }

    private void assertions(BewerbungsDTO bewerbungsDataDTO, boolean isStudent) {
        assertEquals(1, bewerbungsDataDTO.getId());
        assertEquals(stellenanzeigeDTO.getId(), bewerbungsDataDTO.getStellenanzeige().getId());
        assertEquals(stellenanzeigeDTO.getBeschreibung(), bewerbungsDataDTO.getStellenanzeige().getBeschreibung());
        assertEquals(stellenanzeigeDTO.getBezahlung(), bewerbungsDataDTO.getStellenanzeige().getBezahlung());
        assertEquals(stellenanzeigeDTO.getBezeichnung(), bewerbungsDataDTO.getStellenanzeige().getBezeichnung());
        assertEquals(stellenanzeigeDTO.getBeschaeftigungsverhaeltnis(), bewerbungsDataDTO.getStellenanzeige().getBeschaeftigungsverhaeltnis());
        assertEquals(stellenanzeigeDTO.getBeschaeftigungsumfang(), bewerbungsDataDTO.getStellenanzeige().getBeschaeftigungsumfang());
        assertEquals(stellenanzeigeDTO.getErstellungsdatum(), bewerbungsDataDTO.getStellenanzeige().getErstellungsdatum());
        assertEquals(stellenanzeigeDTO.getStart(), bewerbungsDataDTO.getStellenanzeige().getStart());
        assertEquals(stellenanzeigeDTO.getEnde(), bewerbungsDataDTO.getStellenanzeige().getEnde());
        assertEquals(stellenanzeigeDTO.getTaetigkeitsfelder(), bewerbungsDataDTO.getStellenanzeige().getTaetigkeitsfelder());
        assertEquals(TEST, bewerbungsDataDTO.getBewerbungsSchreiben());

        if(isStudent){
            assertEquals(student.getVorname(), bewerbungsDataDTO.getStudent().getVorname());
            assertEquals(student.getNachname(), bewerbungsDataDTO.getStudent().getNachname());
        }
        else {
            assertEquals(unternehmen.getName(), bewerbungsDataDTO.getStellenanzeige().getUnternehmen().getName());
        }
    }

    @Test
    public void testCreateBewerbungsListUnternhemen() throws ProfileException {
        // Execute
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        assertEquals(bewerbungsListFactory, BewerbungsListFactory.getInstance());

        List<BewerbungsDTO> actualCreateBewerbungsListResult = bewerbungsListFactory.createBewerbungsDTOs(bewerbungsList);
        for (BewerbungsDTO bewerbungsDataDTO : actualCreateBewerbungsListResult) {
            assertions(bewerbungsDataDTO, false);
        }
    }
}
