package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QualifikationTest {

    public static final String BESCHREIBUNG = "Test";
    public static final String TEST_2 = "Test2";

    @Test
    public void testMissingCode(){
        Qualifikation qualifikation = Qualifikation.builder()
                .id(1)
                .beschreibung(BESCHREIBUNG)
                .bezeichnung(BESCHREIBUNG)
                .beschaftigungsverhaltnis(BESCHREIBUNG)
                .bis(LocalDate.of(2020, 1, 1))
                .von(LocalDate.of(2020, 1, 1))
                .institution(BESCHREIBUNG)
                .student(null)
                .build();

        assertEquals(qualifikation.hashCode(), qualifikation.hashCode());
        assertNotEquals(qualifikation.hashCode(), Objects.hash(qualifikation.getId(), qualifikation.getBeschreibung(), qualifikation.getBezeichnung(), qualifikation.getBeschaftigungsverhaltnis(), qualifikation.getBis(), qualifikation.getVon(), qualifikation.getInstitution(), qualifikation.getStudent()));
        assertNotEquals(null, qualifikation);
        assertNotEquals(qualifikation, new Object());
        assertNotEquals(qualifikation, Qualifikation.builder().build());

        qualifikation.removeStudent(new Student());
        assertThrows(NullPointerException.class, () -> qualifikation.getStudent().getId());
    }

    @Test
    public void testEquals(){
        Qualifikation qualifikation = Qualifikation.builder()
                .id(1)
                .beschreibung(BESCHREIBUNG)
                .bezeichnung(BESCHREIBUNG)
                .beschaftigungsverhaltnis(BESCHREIBUNG)
                .bis(LocalDate.of(2020, 1, 1))
                .von(LocalDate.of(2020, 1, 1))
                .institution(BESCHREIBUNG)
                .student(new Student())
                .build();

        Qualifikation qualifikation2 = Qualifikation.builder()
                .id(1)
                .beschreibung(BESCHREIBUNG)
                .bezeichnung(BESCHREIBUNG)
                .beschaftigungsverhaltnis(BESCHREIBUNG)
                .bis(LocalDate.of(2020, 1, 1))
                .von(LocalDate.of(2020, 1, 1))
                .institution(BESCHREIBUNG)
                .student(new Student())
                .build();

        assertEquals(qualifikation, qualifikation2);
        assertEquals(qualifikation.hashCode(), qualifikation2.hashCode());
        assertNotEquals(qualifikation, null);
        assertNotEquals(qualifikation, new Object());
        assertNotEquals(qualifikation, Qualifikation.builder().build());

        qualifikation2.setId(2);
        assertNotEquals(qualifikation, qualifikation2);
        qualifikation2.setId(1);
        qualifikation2.setBeschreibung(TEST_2);
        assertNotEquals(qualifikation, qualifikation2);
        qualifikation2.setBeschreibung(BESCHREIBUNG);
        qualifikation2.setBezeichnung(TEST_2);
        assertNotEquals(qualifikation, qualifikation2);
        qualifikation2.setBezeichnung(BESCHREIBUNG);
        qualifikation2.setBeschaftigungsverhaltnis(TEST_2);
        assertNotEquals(qualifikation, qualifikation2);
        qualifikation2.setBeschaftigungsverhaltnis(BESCHREIBUNG);
        qualifikation2.setBis(LocalDate.of(2020, 1, 2));
        assertNotEquals(qualifikation, qualifikation2);
        qualifikation2.setBis(LocalDate.of(2020, 1, 1));
        qualifikation2.setVon(LocalDate.of(2020, 1, 2));
        assertNotEquals(qualifikation, qualifikation2);
    }
}
