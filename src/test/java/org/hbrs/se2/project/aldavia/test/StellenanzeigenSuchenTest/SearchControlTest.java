package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class SearchControlTest {
    private static final String TEST = "Test";
    private static final String TEST2 = "Test2";
    private static final String TEST3 = "Test3";
    private static final LocalDate TEST_DATE = LocalDate.of(2020, 1, 1);
    private static final LocalDate TEST_DATE2 = LocalDate.of(2020, 1, 2);
    public static final String SOFTWARE_ENTWICKLUNG = "Software Entwicklung";
    public static final String PROJEKTMANAGEMENT = "Projektmanagement";
    public static final String IT_CONSULTING = "IT-Consulting";
    public static final String IT_ADMINISTRATION = "IT-Administration";
    @Mock
    private StudentService studentService;

    @Mock
    private StellenanzeigenService stellenanzeigenService;

    @Mock
    private BewerbungsControl bewerbungsControl;

    private SearchControl searchControl;

    private Stellenanzeige stellenanzeige1;
    private Stellenanzeige stellenanzeige2;
    private Stellenanzeige stellenanzeige3;
    @BeforeEach
    public void setup() {
        searchControl = new SearchControl(bewerbungsControl,studentService, stellenanzeigenService);
    }

    private List<Stellenanzeige> setupStellenanzeigen(){
        Unternehmen unternehmen = Unternehmen.builder()
                .id(1)
                .name(TEST)
                .user(User.builder()
                        .userid(TEST)
                        .build())
                .build();

        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung(SOFTWARE_ENTWICKLUNG)
                .build();

        Taetigkeitsfeld taetigkeitsfeld2 = Taetigkeitsfeld.builder()
                .bezeichnung(PROJEKTMANAGEMENT)
                .build();

        Taetigkeitsfeld taetigkeitsfeld3 = Taetigkeitsfeld.builder()
                .bezeichnung(IT_CONSULTING)
                .build();

        Taetigkeitsfeld taetigkeitsfeld4 = Taetigkeitsfeld.builder()
                .bezeichnung(IT_ADMINISTRATION)
                .build();

        List<Taetigkeitsfeld> taetigkeitsfeldListStellenanzeige1 = new ArrayList<>();
        taetigkeitsfeldListStellenanzeige1.add(taetigkeitsfeld);

        List<Taetigkeitsfeld> taetigkeitsfeldListStellenanzeige2 = new ArrayList<>();
        taetigkeitsfeldListStellenanzeige2.add(taetigkeitsfeld2);
        taetigkeitsfeldListStellenanzeige2.add(taetigkeitsfeld3);

        List<Taetigkeitsfeld> taetigkeitsfeldListStellenanzeige3 = new ArrayList<>();
        taetigkeitsfeldListStellenanzeige3.add(taetigkeitsfeld4);
        taetigkeitsfeldListStellenanzeige3.add(taetigkeitsfeld);

        stellenanzeige1 = Stellenanzeige.builder()
                .id(1)
                .beschreibung(TEST)
                .unternehmen_stellenanzeigen(unternehmen)
                .beschaeftigungsumfang(TEST)
                .bezeichnung(TEST)
                .beschaeftigungsverhaeltnis(TEST)
                .bezahlung(TEST)
                .start(TEST_DATE)
                .ende(TEST_DATE2)
                .erstellungsdatum(TEST_DATE)
                .taetigkeitsfelder(taetigkeitsfeldListStellenanzeige1)
                .build();

        stellenanzeige2 = Stellenanzeige.builder()
                .id(2)
                .beschreibung(TEST2)
                .unternehmen_stellenanzeigen(unternehmen)
                .beschaeftigungsumfang(TEST2)
                .bezeichnung(TEST2)
                .beschaeftigungsverhaeltnis(TEST2)
                .bezahlung(TEST2)
                .start(TEST_DATE)
                .ende(TEST_DATE2)
                .taetigkeitsfelder(taetigkeitsfeldListStellenanzeige2)
                .erstellungsdatum(TEST_DATE)
                .build();

        stellenanzeige3 = Stellenanzeige.builder()
                .id(3)
                .beschreibung(TEST3)
                .unternehmen_stellenanzeigen(unternehmen)
                .beschaeftigungsumfang(TEST3)
                .bezeichnung(TEST3)
                .beschaeftigungsverhaeltnis(TEST3)
                .bezahlung(TEST3)
                .start(TEST_DATE)
                .ende(TEST_DATE2)
                .taetigkeitsfelder(taetigkeitsfeldListStellenanzeige3)
                .erstellungsdatum(TEST_DATE)
                .build();

        List<Stellenanzeige> stellenanzeigen = new ArrayList<>();
        stellenanzeigen.add(stellenanzeige1);
        stellenanzeigen.add(stellenanzeige2);
        stellenanzeigen.add(stellenanzeige3);

        return stellenanzeigen;
    }

    private Student setupStudent(){

        Taetigkeitsfeld taetigkeitsfeld3 = Taetigkeitsfeld.builder()
                .bezeichnung(IT_CONSULTING)
                .build();

        Kenntnis kenntnis = Kenntnis.builder()
                .bezeichnung("Java")
                .build();

        Kenntnis kenntnis2 = Kenntnis.builder()
                .bezeichnung(PROJEKTMANAGEMENT)
                .build();

        Kenntnis kenntnis3 = Kenntnis.builder()
                .bezeichnung(IT_ADMINISTRATION)
                .build();

        return Student.builder()
                .kenntnisse(List.of(kenntnis, kenntnis2, kenntnis3))
                .taetigkeitsfelder(List.of(taetigkeitsfeld3))
                .build();
    }

    @Test
    public void testGetAllStellenanzeigen(){
        given(stellenanzeigenService.getStellenanzeigen()).willReturn(setupStellenanzeigen());
        List<StellenanzeigeDTO> stellenanzeigen = searchControl.getAllStellenanzeigen();
        assertEquals(3, stellenanzeigen.size());

        // Teste, ob die richtigen Daten übergeben wurden
        // Teste Beschreibung
        assertEquals(TEST, stellenanzeigen.get(0).getBeschreibung());
        assertEquals(TEST2, stellenanzeigen.get(1).getBeschreibung());
        assertEquals(TEST3, stellenanzeigen.get(2).getBeschreibung());

        // Teste Bezeichnung
        assertEquals(TEST, stellenanzeigen.get(0).getBezeichnung());
        assertEquals(TEST2, stellenanzeigen.get(1).getBezeichnung());
        assertEquals(TEST3, stellenanzeigen.get(2).getBezeichnung());

        // Teste Beschaeftigungsumfang
        assertEquals(TEST, stellenanzeigen.get(0).getBeschaeftigungsumfang());
        assertEquals(TEST2, stellenanzeigen.get(1).getBeschaeftigungsumfang());
        assertEquals(TEST3, stellenanzeigen.get(2).getBeschaeftigungsumfang());

        // Teste Beschaeftigungsverhaeltnis
        assertEquals(TEST, stellenanzeigen.get(0).getBeschaeftigungsverhaeltnis());
        assertEquals(TEST2, stellenanzeigen.get(1).getBeschaeftigungsverhaeltnis());
        assertEquals(TEST3, stellenanzeigen.get(2).getBeschaeftigungsverhaeltnis());

        // Teste Bezahlung
        assertEquals(TEST, stellenanzeigen.get(0).getBezahlung());
        assertEquals(TEST2, stellenanzeigen.get(1).getBezahlung());
        assertEquals(TEST3, stellenanzeigen.get(2).getBezahlung());

        // Teste Start
        assertEquals(TEST_DATE, stellenanzeigen.get(0).getStart());
        assertEquals(TEST_DATE, stellenanzeigen.get(1).getStart());
        assertEquals(TEST_DATE, stellenanzeigen.get(2).getStart());

        // Teste Ende
        assertEquals(TEST_DATE2, stellenanzeigen.get(0).getEnde());
        assertEquals(TEST_DATE2, stellenanzeigen.get(1).getEnde());
        assertEquals(TEST_DATE2, stellenanzeigen.get(2).getEnde());

        // Teste Erstellungsdatum
        assertEquals(TEST_DATE, stellenanzeigen.get(0).getErstellungsdatum());
        assertEquals(TEST_DATE, stellenanzeigen.get(1).getErstellungsdatum());
        assertEquals(TEST_DATE, stellenanzeigen.get(2).getErstellungsdatum());

        // Teste Unternehmen
        assertEquals(TEST, stellenanzeigen.get(0).getUnternehmen().getName());
        assertEquals(TEST, stellenanzeigen.get(1).getUnternehmen().getName());
        assertEquals(TEST, stellenanzeigen.get(2).getUnternehmen().getName());

        // Teste ID
        assertEquals(1, stellenanzeigen.get(0).getId());
        assertEquals(2, stellenanzeigen.get(1).getId());
        assertEquals(3, stellenanzeigen.get(2).getId());

        // Teste Taetigkeitsfelder
        assertEquals(1, stellenanzeigen.get(0).getTaetigkeitsfelder().size());
        assertEquals(2, stellenanzeigen.get(1).getTaetigkeitsfelder().size());
        assertEquals(2, stellenanzeigen.get(2).getTaetigkeitsfelder().size());

        assertEquals(SOFTWARE_ENTWICKLUNG, stellenanzeigen.get(0).getTaetigkeitsfelder().get(0).getName());
        assertEquals(PROJEKTMANAGEMENT, stellenanzeigen.get(1).getTaetigkeitsfelder().get(0).getName());
        assertEquals(IT_CONSULTING, stellenanzeigen.get(1).getTaetigkeitsfelder().get(1).getName());
        assertEquals(IT_ADMINISTRATION, stellenanzeigen.get(2).getTaetigkeitsfelder().get(0).getName());
        assertEquals(SOFTWARE_ENTWICKLUNG, stellenanzeigen.get(2).getTaetigkeitsfelder().get(1).getName());
    }

    @Test
    public void testRecommandationSort() throws ProfileException {
        given(stellenanzeigenService.getStellenanzeigen()).willReturn(setupStellenanzeigen());
        given(studentService.getStudent(TEST)).willReturn(setupStudent());

        List<StellenanzeigeDTO> stellenanzeigeDTOS = searchControl.getRecommendedStellenanzeigen(TEST);
        assertEquals(3, stellenanzeigeDTOS.size());
        assertEquals(stellenanzeige2.getId(), stellenanzeigeDTOS.get(0).getId());
        assertEquals(stellenanzeige3.getId(), stellenanzeigeDTOS.get(1).getId());
        assertEquals(stellenanzeige1.getId(), stellenanzeigeDTOS.get(2).getId());

        List<StellenanzeigeDTO> stellenanzeigeDTOSNotSorted = searchControl.getRecommendedStellenanzeigen("GibtsNicht");
        assertEquals(3, stellenanzeigeDTOSNotSorted.size());
        assertEquals(stellenanzeige1.getId(), stellenanzeigeDTOSNotSorted.get(0).getId());
        assertEquals(stellenanzeige2.getId(), stellenanzeigeDTOSNotSorted.get(1).getId());
        assertEquals(stellenanzeige3.getId(), stellenanzeigeDTOSNotSorted.get(2).getId());
    }

    @Test
    public void getBewerbungsControl(){
        assertEquals(bewerbungsControl, searchControl.getBewerbungsControl());
    }
}
