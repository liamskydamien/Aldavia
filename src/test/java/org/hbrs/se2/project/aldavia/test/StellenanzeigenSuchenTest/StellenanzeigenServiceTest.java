package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.TaetigkeitsfeldService;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class StellenanzeigenServiceTest {

    private StellenanzeigenService stellenanzeigenService;

    @Mock
    private TaetigkeitsfeldService taetigkeitsfeldService;

    @Mock
    private StellenanzeigeRepository stellenanzeigeRepository;


    @BeforeEach
    public void setUp() {
        stellenanzeigenService = new StellenanzeigenService(stellenanzeigeRepository, taetigkeitsfeldService);
    }

    @AfterEach
    public void tearDown() {
        stellenanzeigenService = null;
    }

    @Test
    public void testRoundTripStellenanzeige() throws ProfileException, StellenanzeigenException {

        // Setup

        TaetigkeitsfeldDTO taetigkeitsfeldDTO = TaetigkeitsfeldDTO.builder()
                .name("Java")
                .build();

        UnternehmenProfileDTO unternehmenProfileDTO = UnternehmenProfileDTO.builder()
                .username("Aldavia")
                .build();

        StellenanzeigeDTO stellenanzeigeDTO = StellenanzeigeDTO.builder()
                .bezeichnung("Java")
                .beschreibung("Java")
                .start(LocalDate.now())
                .ende(LocalDate.now().plusDays(10))
                .bezahlung("1000")
                .beschaeftigungsumfang("Vollzeit")
                .taetigkeitsfelder(List.of(taetigkeitsfeldDTO))
                .beschaeftigungsverhaeltnis("Festanstellung")
                .unternehmen(unternehmenProfileDTO)
                .id(1)
                .build();

        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("Java")
                .build();

        Unternehmen unternehmen = Unternehmen.builder()
                .name("Aldavia")
                .user(User.builder()
                        .userid("Aldavia")
                        .build())
                .build();

        Stellenanzeige reference = Stellenanzeige.builder()
                .bezeichnung("Java")
                .beschreibung("Java")
                .start(LocalDate.now())
                .ende(LocalDate.now().plusDays(10))
                .bezahlung("1000")
                .beschaeftigungsumfang("Vollzeit")
                .taetigkeitsfelder(List.of(taetigkeitsfeld))
                .beschaeftigungsverhaeltnis("Festanstellung")
                .id(1)
                .unternehmen_stellenanzeigen(unternehmen)
                .build();

        Stellenanzeige referenceWithoutTaetigkeitsfeld = Stellenanzeige.builder()
                .bezeichnung("Java")
                .beschreibung("Java")
                .start(LocalDate.now())
                .ende(LocalDate.now().plusDays(10))
                .bezahlung("1000")
                .beschaeftigungsumfang("Vollzeit")
                .taetigkeitsfelder(new ArrayList<>())
                .beschaeftigungsverhaeltnis("Festanstellung")
                .id(1)
                .unternehmen_stellenanzeigen(unternehmen)
                .build();

        Set<Stellenanzeige> set = new HashSet<>();
        set.add(reference);
        unternehmen.setStellenanzeigen(set);

        // Mockito
        given(taetigkeitsfeldService.deleteTaetigkeitsfeldFromStellenanzeige(taetigkeitsfeld, reference)).willReturn(reference);
        given(taetigkeitsfeldService.addTaetigkeitsfeldToStellenanzeige(taetigkeitsfeldDTO, reference)).willReturn(reference);
        given(stellenanzeigeRepository.save(any(Stellenanzeige.class))).willReturn(reference);
        given(stellenanzeigeRepository.findById(1)).willReturn(Optional.of(reference));
        given(stellenanzeigeRepository.findById(2)).willReturn(Optional.empty());
        given(stellenanzeigeRepository.findAll()).willReturn(List.of(reference));
        given(taetigkeitsfeldService.deleteTaetigkeitsfeldFromStellenanzeige(taetigkeitsfeld, reference)).willReturn(referenceWithoutTaetigkeitsfeld);

        // Add

        stellenanzeigenService.addStellenanzeige(stellenanzeigeDTO, unternehmen);

        // Read

        Stellenanzeige stellenanzeige = stellenanzeigenService.getStellenanzeige(stellenanzeigeDTO);

        assertEquals(stellenanzeigeDTO.getBezeichnung(), stellenanzeige.getBezeichnung());
        assertEquals(stellenanzeigeDTO.getBeschreibung(), stellenanzeige.getBeschreibung());
        assertEquals(stellenanzeigeDTO.getStart(), stellenanzeige.getStart());
        assertEquals(stellenanzeigeDTO.getEnde(), stellenanzeige.getEnde());
        assertEquals(stellenanzeigeDTO.getBezahlung(), stellenanzeige.getBezahlung());
        assertEquals(stellenanzeigeDTO.getBeschaeftigungsumfang(), stellenanzeige.getBeschaeftigungsumfang());
        assertEquals(stellenanzeigeDTO.getBeschaeftigungsverhaeltnis(), stellenanzeige.getBeschaeftigungsverhaeltnis());
        assertEquals(stellenanzeigeDTO.getTaetigkeitsfelder().get(0).getName(), stellenanzeige.getTaetigkeitsfelder().get(0).getBezeichnung());

        List<Stellenanzeige> stellenanzeigen = stellenanzeigenService.getStellenanzeigen();
        assertEquals(stellenanzeigeDTO.getBezeichnung(), stellenanzeigen.get(0).getBezeichnung());
        assertEquals(stellenanzeigeDTO.getBeschreibung(), stellenanzeigen.get(0).getBeschreibung());
        assertEquals(stellenanzeigeDTO.getStart(), stellenanzeigen.get(0).getStart());
        assertEquals(stellenanzeigeDTO.getEnde(), stellenanzeigen.get(0).getEnde());
        assertEquals(stellenanzeigeDTO.getBezahlung(), stellenanzeigen.get(0).getBezahlung());
        assertEquals(stellenanzeigeDTO.getBeschaeftigungsumfang(), stellenanzeigen.get(0).getBeschaeftigungsumfang());
        assertEquals(stellenanzeigeDTO.getBeschaeftigungsverhaeltnis(), stellenanzeigen.get(0).getBeschaeftigungsverhaeltnis());
        assertEquals(stellenanzeigeDTO.getTaetigkeitsfelder().get(0).getName(), stellenanzeigen.get(0).getTaetigkeitsfelder().get(0).getBezeichnung());

        StellenanzeigeDTO testException = StellenanzeigeDTO.builder()
                .id(2)
                .build();

        StellenanzeigenException stellenanzeigen1Exception = assertThrows(StellenanzeigenException.class, () -> stellenanzeigenService.getStellenanzeige(testException));
        assertEquals(stellenanzeigen1Exception.getType(), StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND);

        // Delete

        stellenanzeigenService.deleteStellenanzeige(stellenanzeigeDTO);

    }
}
