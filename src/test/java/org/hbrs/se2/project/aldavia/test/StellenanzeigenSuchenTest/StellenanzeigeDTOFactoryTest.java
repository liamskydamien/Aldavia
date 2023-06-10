package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StellenanzeigeDTOFactoryTest {

    public static final String BEZEICHNUNG = "bezeichnung";
    public static final String BESCHREIBUNG = "beschreibung";
    public static final String BESCHAEFTIGUNGSVERHAELTNIS = "beschaeftigungsverhaeltnis";
    public static final String BEZAHLUNG = "bezahlung";
    public static final String BESCHAEFTIGUNGSUMFANG = "beschaeftigungsumfang";
    public static final String NAME = "name";

    private final LocalDate START = LocalDate.of(2020, 1, 1);
    private final LocalDate ENDE = LocalDate.of(2020, 2, 1);

    @Test
    public void testCreateStellenanzeigeDTO() {
        // Teste getInstance
        StellenanzeigeDTOFactory stellenanzeigeDTOFactory = StellenanzeigeDTOFactory.getInstance();
        assertEquals(StellenanzeigeDTOFactory.class, stellenanzeigeDTOFactory.getClass());
        assertEquals(StellenanzeigeDTOFactory.getInstance(), stellenanzeigeDTOFactory);

        // Teste createStellenanzeigeDTO
        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .bezeichnung(BEZEICHNUNG)
                .beschreibung(BESCHREIBUNG)
                .beschaeftigungsverhaeltnis(BESCHAEFTIGUNGSVERHAELTNIS)
                .start(START)
                .ende(ENDE)
                .erstellungsdatum(START)
                .bezahlung(BEZAHLUNG)
                .beschaeftigungsumfang(BESCHAEFTIGUNGSUMFANG)
                .taetigkeitsfelder(
                        List.of(Taetigkeitsfeld.builder()
                        .bezeichnung(BEZEICHNUNG)
                        .build()))
                .unternehmen_stellenanzeigen(
                        Unternehmen.builder()
                                .name(NAME)
                                .user(
                                        User.builder()
                                                .userid(NAME)
                                                .build())
                                .build())
                .build();

        StellenanzeigeDTO stellenanzeigeDTO = stellenanzeigeDTOFactory.createStellenanzeigeDTO(stellenanzeige);

        assertEquals(1, stellenanzeigeDTO.getId());
        assertEquals(BEZEICHNUNG, stellenanzeigeDTO.getBezeichnung());
        assertEquals(BESCHREIBUNG, stellenanzeigeDTO.getBeschreibung());
        assertEquals(BESCHAEFTIGUNGSVERHAELTNIS, stellenanzeigeDTO.getBeschaeftigungsverhaeltnis());
        assertEquals(START, stellenanzeigeDTO.getStart());
        assertEquals(ENDE, stellenanzeigeDTO.getEnde());
        assertEquals(START, stellenanzeigeDTO.getErstellungsdatum());
        assertEquals(BEZAHLUNG, stellenanzeigeDTO.getBezahlung());
        assertEquals(BESCHAEFTIGUNGSUMFANG, stellenanzeigeDTO.getBeschaeftigungsumfang());
        assertEquals(1, stellenanzeigeDTO.getTaetigkeitsfelder().size());
        assertEquals(BEZEICHNUNG, stellenanzeigeDTO.getTaetigkeitsfelder().get(0).getName());
        assertEquals(NAME, stellenanzeigeDTO.getUnternehmen().getName());

    }
}
