package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.junit.jupiter.api.Test;

public class StellenanzeigenDataDTOFactoryTest {
    @Test
    public void testCreateStellenanzeigenDataDTO() {
        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .bezeichnung("Titel")
                .beschreibung("Beschreibung")
                .build();
    }
}
