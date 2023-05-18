package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.util.DTOTransformator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestUtilityStudentDTO {

    private Student student;


    @BeforeEach
    public void setUp() {
        // Setup

        // Create User
        User user = new User();
        user.setUserid("test_user3");
        user.setPassword("test_user3");
        user.setEmail("test@test_user3.de");

        // Create Student
        student = new Student();
        student.setVorname("Guido");
        student.setNachname("Müller");
        student.setMatrikelNummer("12345678901");
        student.setGeburtsdatum(LocalDate.of(1990, 1, 1));
        student.setStudienbeginn(LocalDate.of(2010, 1, 1));
        student.setStudiengang("Informatik");
        student.setLebenslauf("Lebenslauf");
        student.setUser(user);

        List<Student> students = new ArrayList<>();
        students.add(student);

        // Create Kenntnis and add to student
        Kenntnis kenntnis = new Kenntnis();
        kenntnis.setBezeichnung("Java_Test");
        kenntnis.setStudenten(students);
        String kenntnisId = "Java_Test";

        List<Kenntnis> kenntnisse = new ArrayList<>();
        kenntnisse.add(kenntnis);
        student.setKenntnisse(kenntnisse);

        // Create qualifikation and add to student
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBezeichnung("Utility");
        qualifikation.setBereich("Utility");

        List<Qualifikation> qualifikationen = new ArrayList<>();
        qualifikationen.add(qualifikation);
        qualifikation.setStudenten(students);
        student.setQualifikationen(qualifikationen);

        //Create taetigkeitsfeld and add to student
        Taetigkeitsfeld taetigkeitsfeld = new Taetigkeitsfeld();
        taetigkeitsfeld.setBezeichnung("Utility");
        List<Taetigkeitsfeld> taetigkeitsfelder = new ArrayList<>();
        taetigkeitsfelder.add(taetigkeitsfeld);
        taetigkeitsfeld.setStudenten(students);
        student.setTaetigkeitsfelder(taetigkeitsfelder);

        // Create sprache and add to student
        Sprache sprache = new Sprache();
        sprache.setName("Utility");
        sprache.setLevel("C1");
        List<Sprache> sprachen = new ArrayList<>();
        sprachen.add(sprache);
        sprache.setStudenten(students);
        student.setSprachen(sprachen);

    }

    @AfterEach
    public void tearDown() {
        // Clean up
        student = null;
    }

    @Test
    public void testTransformDTOStudentDTO() {
        StudentProfileDTO studentProfileDTO = DTOTransformator.transformStudentProfileDTO(student);
        assertEquals(studentProfileDTO.getVorname(), student.getVorname());
        assertEquals(studentProfileDTO.getNachname(), student.getNachname());
        assertEquals(studentProfileDTO.getMatrikelNummer(), student.getMatrikelNummer());
        assertEquals(studentProfileDTO.getStudiengang(), student.getStudiengang());
        assertEquals(studentProfileDTO.getEmail(), student.getUser().getEmail());
        assertEquals(studentProfileDTO.getGeburtsdatum(), student.getGeburtsdatum());
        assertEquals(studentProfileDTO.getStudienbeginn(), student.getStudienbeginn());
        assertEquals(studentProfileDTO.getBeschreibung(), student.getUser().getBeschreibung());
        assertEquals(studentProfileDTO.getTelefonnummer(), student.getUser().getPhone());
        assertEquals(studentProfileDTO.getKenntnisse().get(0).getBezeichnung(), student.getKenntnisse().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBezeichnung(), student.getQualifikationen().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBereich(), student.getQualifikationen().get(0).getBereich());
        assertEquals(studentProfileDTO.getTaetigkeitsfelder().get(0).getBezeichnung(), student.getTaetigkeitsfelder().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getSprachen().get(0).getBezeichnung(), student.getSprachen().get(0).getName());
        assertEquals(studentProfileDTO.getSprachen().get(0).getLevel(), student.getSprachen().get(0).getLevel());
    }

}
