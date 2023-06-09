package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.factories.BewerbungsListFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BewerbungsListFactoryTest {
    @Test
    public void testCreateBewerbungsList() {
        // Setup
        Student student = Student.builder()
                .id(1)
                .build();

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .build();

        Bewerbung bewerbung1 = Bewerbung.builder()
                .id(1)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben("test")
                .datum(LocalDate.now())
                .build();

        Bewerbung bewerbung2 = Bewerbung.builder()
                .id(1)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben("test")
                .datum(LocalDate.now())
                .build();

        List<Bewerbung> bewerbungsList = new ArrayList<>();
        bewerbungsList.add(bewerbung1);
        bewerbungsList.add(bewerbung2);

        // Execute
        BewerbungsListFactory bewerbungsListFactory = BewerbungsListFactory.getInstance();
        assertEquals(bewerbungsListFactory, BewerbungsListFactory.getInstance());

        List<BewerbungsDTO> actualCreateBewerbungsListResult = bewerbungsListFactory.createBewerbungsDTOs(bewerbungsList);
        for (BewerbungsDTO bewerbungsDTO : actualCreateBewerbungsListResult) {
            assertEquals(1, bewerbungsDTO.getId());
            assertEquals(1, bewerbungsDTO.getStudentId());
            assertEquals(1, bewerbungsDTO.getStellenanzeigeId());
            assertEquals("test", bewerbungsDTO.getBewerbungsSchreiben());
        }
    }
}
