package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentTest {
    @Test
    public void testMissingCode(){

        Bewerbung bewerbung = new Bewerbung();
        Taetigkeitsfeld taetigkeitsfeld = new Taetigkeitsfeld();
        Kenntnis kenntnis = new Kenntnis();
        Sprache sprache = new Sprache();
        Qualifikation qualifikation = new Qualifikation();

        Student student = new Student();
        student.removeBewerbung(bewerbung);
        assertThrows(NullPointerException.class, () -> student.getBewerbungen().size());
        student.addBewerbung(bewerbung);
        assertEquals(1, student.getBewerbungen().size());
        student.removeTaetigkeitsfeld(taetigkeitsfeld);
        assertThrows(NullPointerException.class, () -> student.getTaetigkeitsfelder().size());
        student.addTaetigkeitsfeld(taetigkeitsfeld);
        assertEquals(1, student.getTaetigkeitsfelder().size());
        student.removeQualifikation(qualifikation);
        assertThrows(NullPointerException.class, () -> student.getQualifikationen().size());
        student.addQualifikation(qualifikation);
        assertEquals(1, student.getQualifikationen().size());
        student.removeKenntnis(kenntnis);
        assertThrows(NullPointerException.class, () -> student.getKenntnisse().size());
        student.addKenntnis(kenntnis);
        assertEquals(1, student.getKenntnisse().size());
        student.removeSprache(sprache);
        assertThrows(NullPointerException.class, () -> student.getSprachen().size());
        student.addSprache(sprache);
        assertEquals(1, student.getSprachen().size());
    }

    @Test
    public void testEquals(){
        Student student = new Student();
        student.setId(1);
        Student student1 = new Student();
        student1.setId(1);
        assertEquals(student, student1);
        assertNotEquals(student, null);
        assertNotEquals(student, new Object());
    }
}
