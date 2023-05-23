package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.LocalDate;
@SpringBootTest
@Transactional
public class StellenanzeigeTest2 {


    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    private Stellenanzeige stellenanzeige;
    private Taetigkeitsfeld taetigkeitsfeld;

    @BeforeEach
    public void setup() {
        // Create a test Unternehmen
        Unternehmen unternehmen = new Unternehmen();
        // Set up the unternehmen object with necessary properties

        // Create a test Stellenanzeige
        stellenanzeige = new Stellenanzeige();
        stellenanzeige.setBezeichnung("Test Stellenanzeige");
        stellenanzeige.setBeschreibung("Test Beschreibung");
        stellenanzeige.setBeschaeftigungsverhaeltnis("Test Beschaeftigungsverhaeltnis");
        stellenanzeige.setStart(LocalDate.now());
        stellenanzeige.setEnde(LocalDate.now().plusMonths(3));
        stellenanzeige.setErstellungsdatum(LocalDate.now());
        stellenanzeige.setBezahlung("Test Bezahlung");
        stellenanzeige.setBeschaeftigungsumfang("Test Beschaeftigungsumfang");
        stellenanzeige.setUnternehmen(unternehmen);

        // Create a test Taetigkeitsfeld
        taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("Enticklung")
                .build();

        // Set up the taetigkeitsfeld object with necessary properties

        // Add the taetigkeitsfeld to the stellenanzeige
        stellenanzeige.addTaetigkeitsfeld(taetigkeitsfeld);




    }

    @Test
    public void testFindById() {
        stellenanzeigeRepository.save(stellenanzeige);

        // Retrieve the test Stellenanzeige from the repository by its ID
        Stellenanzeige retrievedStellenanzeige = stellenanzeigeRepository.findById(stellenanzeige.getId()).orElse(null);

        // Assert that the retrieved Stellenanzeige is not null
        Assertions.assertNotNull(retrievedStellenanzeige);

        // Assert that the retrieved Stellenanzeige has the same bezeichnung as the original Stellenanzeige
        Assertions.assertEquals("Test Stellenanzeige", retrievedStellenanzeige.getBezeichnung());

        // Assert that the retrieved Stellenanzeige contains the test Taetigkeitsfeld
        List<Taetigkeitsfeld> retrievedTaetigkeitsfelder = retrievedStellenanzeige.getTaetigkeitsbereiche();
        Assertions.assertTrue(retrievedTaetigkeitsfelder.contains(taetigkeitsfeld));
    }
    // Add more test cases as needed

    @Test
    public void testFindByBezeichnung() {
        // Retrieve the test Stellenanzeige from the repository by its bezeichnung
        Stellenanzeige retrievedStellenanzeige = stellenanzeigeRepository.findByBezeichnung("Test Stellenanzeige").get();

        // Assert that the retrieved Stellenanzeige is not null
        Assertions.assertNotNull(retrievedStellenanzeige);

        // Assert that the retrieved Stellenanzeige has the same bezeichnung as the original Stellenanzeige
        Assertions.assertEquals("Test Stellenanzeige", retrievedStellenanzeige.getBezeichnung());
    }
/*
    @Test
    public void testAddAndRemoveBewerbung() {
        // Create a test Bewerbung
        Bewerbung bewerbung = Bewerbung.builder()
                .datum(LocalDate.now().plusMonths(3))
                        .
                // Set up the bewerbung object with necessary properties

                // Add the bewerbung to the test Stellenanzeige
                        stellenanzeige.addBewerbung(bewerbung);


        // Retrieve the updated test Stellenanzeige from the repository by its ID
        Stellenanzeige retrievedStellenanzeige = stellenanzeigeRepository.findById(stellenanzeige.getId()).orElse(null);

        // Assert that the retrieved Stellenanzeige is not null
        Assertions.assertNotNull(retrievedStellenanzeige);

        // Assert that the retrieved Stellenanzeige contains the added Bewerbung
        List<Bewerbung> retrievedBewerbungen = retrievedStellenanzeige.getBewerbungen();
        Assertions.assertTrue(retrievedBewerbungen.contains(bewerbung));

        // Remove the Bewerbung from the test Stellenanzeige
        stellenanzeige.removeBewerbung(bewerbung);


        // Retrieve the updated test Stellenanzeige from the repository by its ID
        retrievedStellenanzeige = stellenanzeigeRepository.findById(stellenanzeige.getId()).orElse(null);

        // Assert that the retrieved Stellenanzeige is not null
        Assertions.assertNotNull(retrievedStellenanzeige);

        // Assert that the retrieved Stellenanzeige does not contain the removed Bewerbung
        retrievedBewerbungen = retrievedStellenanzeige.getBewerbungen();
        Assertions.assertFalse(retrievedBewerbungen.contains(bewerbung));
    }
    */

}
