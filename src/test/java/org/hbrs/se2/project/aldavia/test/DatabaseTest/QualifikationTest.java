package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QualifikationTest {
    @Test
    public void roundTripTest(){
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBereich("Qualitätsmanagement");
        qualifikation.setBezeichnung("Praktikum in Software-Testing");

    }
}
