package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class AdresseTest {
    @Test
    public void testEquals(){
        Adresse adresse = new Adresse();
        adresse.setId(1);
        Adresse adresse2 = new Adresse();
        adresse2.setId(2);

        assertNotEquals(adresse, adresse2);
        adresse2.setId(1);
        assertEquals(adresse, adresse2);
        assertNotEquals(adresse, null);
        assertNotEquals(adresse, new Object());

        Adresse adresse3 = new Adresse();
        Adresse adresse4 = new Adresse();

        assertEquals(adresse3, adresse4);

    }
}
