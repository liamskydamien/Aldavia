package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QualifikationTest {

    @Autowired
    private QualifikationRepository qualifikationRepository;

    @Test
    public void roundTripTest(){
        // Test create
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBereich("Qualitätsmanagement");
        qualifikation.setBezeichnung("Praktikum in Software-Testing");
        qualifikationRepository.save(qualifikation);

        System.out.println(qualifikationRepository.findAll().get(0).getId());

        // Test Read
        Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(1);
        assertTrue(awaitQualifikation.isPresent());
        assertEquals(qualifikation, awaitQualifikation.get());

        // Test Update
        qualifikation.setBezeichnung("Testmanagement");
        qualifikationRepository.save(qualifikation);

        Optional<Qualifikation> awaitNewQualifikation = qualifikationRepository.findById(1);
        assertTrue(awaitNewQualifikation.isPresent());
        assertEquals(qualifikation, awaitNewQualifikation.get());

        // Test Delete
        qualifikationRepository.deleteById(1);
        assertFalse(qualifikationRepository.existsById(1));
    }

    @Test
    public void negativeTests(){

    }
}
