package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.hbrs.se2.project.aldavia.service.TaetigkeitsfeldService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class TaetigkeitsfeldStellenanzeigenTest {
    @Mock
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private TaetigkeitsfeldService taetigkeitsfeldService;

    @Test
    public void testTaetigkeitsfeldStellenanzeigen() {
        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .bezeichnung("Java Entwickler")
                .beschreibung("Java Entwickler")
                .beschaeftigungsverhaeltnis("Vollzeit")
                .unternehmen_stellenanzeigen(null)
                .taetigkeitsfelder(new ArrayList<>())
                .build();

        TaetigkeitsfeldDTO taetigkeitsfeldDTO = TaetigkeitsfeldDTO.builder()
                .name("Java")
                .build();

        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung(taetigkeitsfeldDTO.getName())
                .stellenanzeigen(new ArrayList<>())
                .build();

        Taetigkeitsfeld taetigkeitsfeld1 = Taetigkeitsfeld.builder()
                .bezeichnung(taetigkeitsfeldDTO.getName())
                .stellenanzeigen(List.of(stellenanzeige))
                .build();

        given(taetigkeitsfeldRepository.findById("Java")).willReturn(Optional.of(taetigkeitsfeld));
        given(taetigkeitsfeldRepository.save(any(Taetigkeitsfeld.class))).willReturn(taetigkeitsfeld1);

        Stellenanzeige stellenanzeige1 = taetigkeitsfeldService.addTaetigkeitsfeldToStellenanzeige(taetigkeitsfeldDTO,stellenanzeige);

        assertEquals(1, stellenanzeige1.getTaetigkeitsfelder().size());
        assertEquals("Java", stellenanzeige1.getTaetigkeitsfelder().get(0).getBezeichnung());
        assertEquals(1, stellenanzeige1.getTaetigkeitsfelder().get(0).getStellenanzeigen().size());

        /*Stellenanzeige stellenanzeige2 = taetigkeitsfeldService.deleteTaetigkeitsfeldFromStellenanzeige(taetigkeitsfeld,stellenanzeige);
        assertEquals(0, stellenanzeige2.getTaetigkeitsfelder().size());*/
    }
}
