package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BewerbungTest {
    @Test
    public void testMissingCode(){
        Bewerbung bewerbung = Bewerbung.builder()
                .bewerbungsSchreiben("Test")
                .id(1)
                .status("Test")
                .build();

        Student student = Student.builder()
                .id(1)
                .build();

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .build();

        assertNull(bewerbung.getStudent());
        bewerbung.setStudent(student);
        assertEquals(student, bewerbung.getStudent());

        assertNull(bewerbung.getStellenanzeige());
        bewerbung.setStellenanzeige(stellenanzeige);
        assertEquals(stellenanzeige, bewerbung.getStellenanzeige());

        Bewerbung bewerbung1 = Bewerbung.builder()
                .bewerbungsSchreiben("Test")
                .id(1)
                .status("Test")
                .student(bewerbung.getStudent())
                .stellenanzeige(bewerbung.getStellenanzeige())
                .build();

        assertEquals(bewerbung, bewerbung1);
        assertNotEquals(bewerbung, new Object());
        assertNotEquals(bewerbung, Bewerbung.builder().build());
        assertNotEquals(bewerbung, null);
    }
}
