package org.hbrs.se2.project.aldavia.test.unternehmenProfileTest;

import org.hbrs.se2.project.aldavia.control.factories.AdressenDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AdressenDTOFactoryTest {

    private final AdressenDTOFactory adressenDTOFactory = AdressenDTOFactory.getInstance();

    @Test
    public void testSingleton() {
        AdressenDTOFactory instance1 = AdressenDTOFactory.getInstance();
        AdressenDTOFactory instance2 = AdressenDTOFactory.getInstance();
        assertEquals(instance1,instance2);
    }

    @Test
    public void testEmpty() {
        assertThrows(NullPointerException.class, () -> adressenDTOFactory.createAdressenDTO(null));
    }

    @Test
    public void testFull(){

        Unternehmen unternehmen = Unternehmen.builder()
                .user(User.builder()
                        .userid("username")
                        .password("password")
                        .email("email")
                        .phone("phone")
                        .beschreibung("beschreibung")
                        .profilePicture("profilePicture")
                        .build())
                .name("name")
                .ap_vorname("ap_vorname")
                .ap_nachname("ap_nachname")
                .webseite("webseite")
                .beschreibung("beschreibung")
                .build();

        Adresse adresse = Adresse.builder()
                .id(1)
                .strasse("strasse")
                .hausnummer("hausnummer")
                .plz("plz")
                .ort("ort")
                .unternehmen(Set.of(unternehmen))
                .build();

        unternehmen.setAdressen(Set.of(adresse));

        AdresseDTO adresseDTO = adressenDTOFactory.createAdressenDTO(adresse);

        assertEquals(adresse.getId(), adresseDTO.getId());
        assertEquals(adresse.getStrasse(), adresseDTO.getStrasse());
        assertEquals(adresse.getHausnummer(), adresseDTO.getHausnummer());
        assertEquals(adresse.getPlz(), adresseDTO.getPlz());
        assertEquals(adresse.getOrt(), adresseDTO.getOrt());
        assertEquals(adresse.getLand(), adresseDTO.getLand());
    }
}
