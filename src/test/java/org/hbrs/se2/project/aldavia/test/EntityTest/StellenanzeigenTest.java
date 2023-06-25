package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StellenanzeigenTest {
    @Test
    public void testAddAndRemoveStuff(){
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        stellenanzeige.removeBewerbung(null);
        assertThrows(Exception.class, () -> stellenanzeige.getBewerbungen().size());
        Bewerbung bewerbung = Bewerbung.builder()
                .id(1)
                .build();
        stellenanzeige.addBewerbung(bewerbung);
        assertEquals(1, stellenanzeige.getBewerbungen().size());
        stellenanzeige.removeBewerbung(bewerbung);
        assertEquals(0, stellenanzeige.getBewerbungen().size());
        stellenanzeige.removeTaetigkeitsfeld(null);
        assertThrows(Exception.class, () -> stellenanzeige.getTaetigkeitsfelder().size());
        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("test")
                .build();
        stellenanzeige.addTaetigkeitsfeld(taetigkeitsfeld);
        assertEquals(1, stellenanzeige.getTaetigkeitsfelder().size());
        stellenanzeige.removeTaetigkeitsfeld(taetigkeitsfeld);
        assertEquals(0, stellenanzeige.getTaetigkeitsfelder().size());
    }

    @Test
    public void testEquals(){
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        Stellenanzeige stellenanzeige2 = new Stellenanzeige();
        assertEquals(stellenanzeige, stellenanzeige2);
        stellenanzeige2.setId(1);
        assertNotEquals(stellenanzeige, stellenanzeige2);
        stellenanzeige.setId(1);
        assertEquals(stellenanzeige, stellenanzeige2);
        assertNotEquals(stellenanzeige, null);
        assertNotEquals(stellenanzeige, new Object());
    }
}
