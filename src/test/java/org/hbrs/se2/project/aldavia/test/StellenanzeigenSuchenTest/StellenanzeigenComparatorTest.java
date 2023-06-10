package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.util.comperators.StellenanzeigenComparator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StellenanzeigenComparatorTest {

    public static final String IT = "IT";
    public static final String BWL = "BWL";
    public static final String JURA = "JURA";
    public static final String MATHE = "MATHE";
    public static final String VWL = "VWL";
    public static final String MUSIK = "MUSIK";

    @Test
    public void testeComparator(){

        //Setup
        List<String> kenntnisse = List.of(IT, BWL, JURA);
        List<String> interessen = List.of(IT, MATHE, VWL, MUSIK);

        TaetigkeitsfeldDTO it = TaetigkeitsfeldDTO.builder()
                .name(IT)
                .build();

        TaetigkeitsfeldDTO bwl = TaetigkeitsfeldDTO.builder()
                .name(BWL)
                .build();

        TaetigkeitsfeldDTO jura = TaetigkeitsfeldDTO.builder()
                .name(JURA)
                .build();

        TaetigkeitsfeldDTO mathe = TaetigkeitsfeldDTO.builder()
                .name(MATHE)
                .build();

        TaetigkeitsfeldDTO vwl = TaetigkeitsfeldDTO.builder()
                .name(VWL)
                .build();

        TaetigkeitsfeldDTO musik = TaetigkeitsfeldDTO.builder()
                .name(MUSIK)
                .build();

        TaetigkeitsfeldDTO kunst = TaetigkeitsfeldDTO.builder()
                .name("KUNST")
                .build();

        TaetigkeitsfeldDTO kultur = TaetigkeitsfeldDTO.builder()
                .name("KULTUR")
                .build();

        StellenanzeigeDTO stellenanzeigeDTO1 = StellenanzeigeDTO.builder()
                .taetigkeitsfelder(List.of(it, bwl)) // 4
                .build();

        StellenanzeigeDTO stellenanzeigeDTO2 = StellenanzeigeDTO.builder()
                .taetigkeitsfelder(List.of(jura, bwl)) // 2
                .build();

        StellenanzeigeDTO stellenanzeigeDTO3 = StellenanzeigeDTO.builder()
                .taetigkeitsfelder(List.of(mathe, vwl)) // 4
                .build();

        StellenanzeigeDTO stellenanzeigeDTO4 = StellenanzeigeDTO.builder()
                .taetigkeitsfelder(List.of(bwl, vwl, musik)) // 5
                .build();

        StellenanzeigeDTO stellenanzeigeDTO5 = StellenanzeigeDTO.builder()
                .taetigkeitsfelder(List.of(kunst, kultur)) // 0
                .build();

        StellenanzeigenComparator stellenanzeigenComparator = new StellenanzeigenComparator(interessen, kenntnisse);

        // Stellenanzeige 1
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO1,stellenanzeigeDTO5) > 0);
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO1,stellenanzeigeDTO2) > 0);
        assertEquals(0, stellenanzeigenComparator.compare(stellenanzeigeDTO1,stellenanzeigeDTO3));
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO1,stellenanzeigeDTO4) > 0);

        // Stellenanzeige 2
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO2,stellenanzeigeDTO1) > 0);
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO2,stellenanzeigeDTO3) > 0);
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO2,stellenanzeigeDTO4) > 0);
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO2,stellenanzeigeDTO5) > 0);

        // Stellenanzeige 3
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO3,stellenanzeigeDTO5) > 0);
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO3,stellenanzeigeDTO2) > 0);
        assertEquals(0, stellenanzeigenComparator.compare(stellenanzeigeDTO3,stellenanzeigeDTO1));
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO3,stellenanzeigeDTO4) > 0);

        // Stellenanzeige 4
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO4,stellenanzeigeDTO1) > 0);
        assertTrue( stellenanzeigenComparator.compare(stellenanzeigeDTO4,stellenanzeigeDTO2) > 0);
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO4,stellenanzeigeDTO3) > 0);
        assertTrue(stellenanzeigenComparator.compare(stellenanzeigeDTO4,stellenanzeigeDTO5) > 0);

        // Stellenanzeige 5
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO5,stellenanzeigeDTO1) > 0);
        assertFalse(stellenanzeigenComparator.compare(stellenanzeigeDTO5,stellenanzeigeDTO2) > 0);
        assertFalse( stellenanzeigenComparator.compare(stellenanzeigeDTO5,stellenanzeigeDTO3) > 0);
        assertFalse( stellenanzeigenComparator.compare(stellenanzeigeDTO5,stellenanzeigeDTO4) > 0);
    }
}
