package org.hbrs.se2.project.aldavia.test.EntityTest;

import okhttp3.Address;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.views.components.AddStellenanzeigeFormComponent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UnternehmenTest {
    @Test
    public void testAddAndRemoveStuff(){
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.removeStellenanzeige(null);
        unternehmen.removeStellenanzeige(new Stellenanzeige());
        unternehmen.removeAdresse(new Adresse());
        unternehmen.removeAdresse(null);
        assertThrows(Exception.class, () -> unternehmen.getStellenanzeigen().size());
        assertThrows(Exception.class, () -> unternehmen.getAdressen().size());

        Stellenanzeige stellenanzeige = new Stellenanzeige();
        unternehmen.addStellenanzeige(stellenanzeige);
        assertEquals(1, unternehmen.getStellenanzeigen().size());

        Adresse adresse = new Adresse();
        unternehmen.addAdresse(adresse);
        assertEquals(1, unternehmen.getAdressen().size());
    }

    @Test
    public void testEquals(){
        Unternehmen unternehmen = new Unternehmen();
        Unternehmen unternehmen2 = new Unternehmen();
        assertEquals(unternehmen, unternehmen2);
        unternehmen2.setId(1);
        assertNotEquals(unternehmen, unternehmen2);
        unternehmen.setId(1);
        assertEquals(unternehmen, unternehmen2);
        assertNotEquals(unternehmen, null);
        assertNotEquals(unternehmen, new Object());
    }
}
