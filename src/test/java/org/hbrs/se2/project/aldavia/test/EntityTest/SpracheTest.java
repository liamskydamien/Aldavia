package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class SpracheTest {
    @Test
    public void testMissingCode(){
        Sprache sprache = Sprache.builder()
                .bezeichnung("Test")
                .level("Test")
                .build();

        Student student = Student.builder()
                .id(1)
                .build();

        sprache.removeStudent(null);
        assertThrows(NullPointerException.class, () -> sprache.getStudents().size());
        sprache.addStudent(student);
        assertEquals(1, sprache.getStudents().size());
        sprache.removeStudent(student);
        assertEquals(0, sprache.getStudents().size());

        Sprache sprache1 = Sprache.builder()
                .bezeichnung("Test")
                .level("Test")
                .students(sprache.getStudents())
                .build();

        assertEquals(sprache, sprache1);
        assertNotEquals(sprache, new Object());
        assertNotEquals(sprache, Sprache.builder().build());
        assertNotEquals(sprache, null);
    }
}
