package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KenntnisseTest {

    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    @Test
    public void testeRoundTrip() {
        try {
            Kenntnis kenntnis = new Kenntnis();
            kenntnis.setBezeichnung("Java");
            kenntnisseRepository.save(kenntnis);
            //Saved in DB?
            assertTrue(kenntnisseRepository.existsById("Java"));

            //Read
            Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById("Java");
            assertTrue(awaitKenntnis.isPresent());
            Kenntnis kenntnisFromDB = awaitKenntnis.get();
            assertEquals("Java", kenntnisFromDB.getBezeichnung());

            //Update
            kenntnisFromDB.setBezeichnung("Java 8");
            kenntnisseRepository.save(kenntnisFromDB);
            awaitKenntnis = kenntnisseRepository.findById("Java 8");
            assertTrue(awaitKenntnis.isPresent());
            kenntnisFromDB = awaitKenntnis.get();
            assertEquals("Java 8", kenntnisFromDB.getBezeichnung());

            //Delete
            kenntnisseRepository.deleteById("Java 8");
            assertFalse(kenntnisseRepository.existsById("Java 8"));
        }
        catch (Exception e) {
            System.out.println("Fehler beim Speichern: " + e.getMessage());
        }
    }
}
