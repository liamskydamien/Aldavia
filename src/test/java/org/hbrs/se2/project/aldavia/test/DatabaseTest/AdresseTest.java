package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.AdresseRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AdresseTest {
    public static final String STRASSE = "Straße";
    public static final String NUMBER = "12345";
    @Autowired
    private UnternehmenRepository unternehmenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdresseRepository adresseRepository;

    private Adresse adresse;

    @BeforeEach
    public void setup() {
        adresse = Adresse.builder()
                .id(1)
                .strasse(STRASSE)
                .hausnummer("123")
                .plz(NUMBER)
                .ort("Ort")
                .land("Land")
                .build();
    }

    @Test
    public void getterAndSetterTest() {
        User userUnternehmen = User.builder()
                .userid("TestTestCompany37")
                .password("password1234567")
                .email("test1234567Untnxfnernehmen@gmail.com")
                .build();
        userRepository.save(userUnternehmen);
        Unternehmen unternehmen = Unternehmen.builder()
                .name("Test GmbH")
                .user(userUnternehmen)
                .build();
        unternehmenRepository.save(unternehmen);


        adresse.setStrasse("Neue Straße");
        adresse.setHausnummer("456");
        adresse.setPlz("54321");
        adresse.setOrt("Neuer Ort");
        adresse.setLand("Neues Land");
        unternehmen.addAdresse(adresse);

        assertEquals("Neue Straße", adresse.getStrasse());
        assertEquals("456", adresse.getHausnummer());
        assertEquals("54321", adresse.getPlz());
        assertEquals("Neuer Ort", adresse.getOrt());
        assertEquals("Neues Land", adresse.getLand());
        assertTrue(adresse.getUnternehmen().contains(unternehmen));
        assertEquals(1, adresse.getUnternehmen().size());
        assertEquals(1, unternehmen.getAdressen().size());

    }

    @Test
    public void removeUnternehmenTest() {
        User userUnternehmen = User.builder()
                .userid("TestTestCompany37")
                .password("password1234567")
                .email("test1234567Untnxfnernehmen@gmail.com")
                .build();
        userRepository.save(userUnternehmen);
        Unternehmen unternehmen = Unternehmen.builder()
                .name("Test GmbH")
                .user(userUnternehmen)
                .build();
        unternehmenRepository.save(unternehmen);

        unternehmen.addAdresse(adresse);

        assertTrue(adresse.getUnternehmen().contains(unternehmen));

        unternehmen.removeAdresse(adresse);

        assertFalse(adresse.getUnternehmen().contains(unternehmen));
        assertEquals(0, adresse.getUnternehmen().size());
        assertFalse(unternehmen.getAdressen().contains(adresse));
        assertEquals(0, unternehmen.getAdressen().size());
    }

    @Test
    public void equalsAndHashCodeTest() {
        Adresse adresse2 = Adresse.builder()
                .id(1)
                .strasse(STRASSE)
                .hausnummer("123")
                .plz(NUMBER)
                .ort("Ort")
                .land("Land")
                .build();

        assertEquals(adresse, adresse2);
        assertEquals(adresse.hashCode(), adresse2.hashCode());

        adresse2.setId(2);
        assertNotEquals(adresse, adresse2);
        assertNotEquals(adresse.hashCode(), adresse2.hashCode());
    }

    @Test
    public void toStringTest() {
        String expectedString = "Straße 123, 12345 Ort, Land";
        assertEquals(expectedString, adresse.toString());
    }

    @Test
    public void testNotNullConstraints() {
        adresse = new Adresse();
        assertThrows(DataIntegrityViolationException.class, () -> adresseRepository.saveAndFlush(adresse));
        adresse.setStrasse(STRASSE);
        assertThrows(DataIntegrityViolationException.class, () -> adresseRepository.saveAndFlush(adresse));
        adresse.setHausnummer("123");
        assertThrows(DataIntegrityViolationException.class, () -> adresseRepository.saveAndFlush(adresse));
        adresse.setPlz(NUMBER);
        assertThrows(DataIntegrityViolationException.class, () -> adresseRepository.saveAndFlush(adresse));
        adresse.setOrt("Ort");
        assertThrows(DataIntegrityViolationException.class, () -> adresseRepository.saveAndFlush(adresse));
        adresse.setLand("Land");
        assertThrows(DataIntegrityViolationException.class, () -> adresseRepository.saveAndFlush(adresse));

    }
}
