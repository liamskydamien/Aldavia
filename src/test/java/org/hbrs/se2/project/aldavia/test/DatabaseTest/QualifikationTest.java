package org.hbrs.se2.project.aldavia.test.DatabaseTest;


import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QualifikationTest {

    @Autowired
    private QualifikationRepository qualifikationRepository;

    @Autowired
    private StudentRepository studentRepository;

    User user = User.builder()
            .email("Student1@qtest.vn")
            .userid("QStudent1")
            .password("qwedfghbn")
            .build();

    Student student = Student.builder()
            .nachname("Nguyen")
            .vorname("Qtest")
            .user(user)
            .matrikelNummer("963852")
            .build();

    Qualifikation qualifikation1 = Qualifikation.builder()
            .bezeichnung("QTester")
            .beschreibung("Ability to write JUnit tests.")
            .bereich("Software Engineering 1")
            .institution("H-BRS")
            .beschaftigungsverhaltnis("Teilzeit")
            .von(LocalDate.of(2023,4, 1))
            .bis(LocalDate.of(2023, 6, 30))
            .build();

    Qualifikation qualifikation2 = Qualifikation.builder()
            .bezeichnung("QTester2")
            .beschreibung("Test Test Test")
            .bereich("Software Engineering")
            .institution("H-BRS")
            .beschaftigungsverhaltnis("Vollzeit")
            .von(LocalDate.of(2022,5, 17))
            .bis(LocalDate.of(2023, 6, 30))
            .build();

    @Test
    public void roundTripTest(){

        // Create / Setup
        qualifikationRepository.save(qualifikation1);
        //Saved in DB?
        assertTrue(qualifikationRepository.existsById(qualifikation1.getId()));

        // Read
        Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(qualifikation1.getId());
        assertTrue(awaitQualifikation.isPresent());
        assertEquals(awaitQualifikation.get().getId(), qualifikation1.getId());
        assertEquals(awaitQualifikation.get().getBezeichnung(), qualifikation1.getBezeichnung());

        // Update
        qualifikation1.setBezeichnung("QtTester");
        qualifikation1.setBereich("Software Engineering 2");
        qualifikation1.setVon(LocalDate.of(2023,3, 31));
        qualifikation1.setBis(LocalDate.of(2023,12, 31));
        qualifikationRepository.save(qualifikation1);
        awaitQualifikation = qualifikationRepository.findById(qualifikation1.getId());
        assertTrue(awaitQualifikation.isPresent());
        assertEquals(awaitQualifikation.get().getId(), qualifikation1.getId());
        assertEquals(awaitQualifikation.get().getBezeichnung(), qualifikation1.getBezeichnung());
        assertEquals(awaitQualifikation.get().getVon(), qualifikation1.getVon());
        assertEquals(awaitQualifikation.get().getBis(), qualifikation1.getBis());

        // Delete
        qualifikationRepository.deleteById(qualifikation1.getId());
        assertFalse(qualifikationRepository.existsById(qualifikation1.getId()));
    }

    @Test
    public void negativeTests(){
        assertThrows(Exception.class, () -> qualifikationRepository.save(null));
        assertThrows(Exception.class, () -> qualifikationRepository.deleteById(-1));
    }

    @Test
    public void studentTest() {
        // Setup
        studentRepository.save(student);

        List<Qualifikation> qualifikationen = new ArrayList<>();
        qualifikationen.add(qualifikation1);
        qualifikationen.add(qualifikation2);

        qualifikation1.setStudent(student);
        qualifikation2.setStudent(student);
        qualifikationRepository.save(qualifikation1);
        qualifikationRepository.save(qualifikation2);


        // Update Student
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);

        // Read
        Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(qualifikation2.getId());
        assertTrue(awaitQualifikation.isPresent());
        assertEquals(awaitQualifikation.get().getId(), qualifikation2.getId());
        assertEquals(awaitQualifikation.get().getBezeichnung(), qualifikation2.getBezeichnung());
        assertEquals(awaitQualifikation.get().getStudent().getId(), qualifikation2.getStudent().getId());

        Optional<Student> awaitQualifikationFromStudent = studentRepository.findById(student.getId());
        assertTrue(awaitQualifikationFromStudent.isPresent());
        assertEquals(awaitQualifikationFromStudent.get().getId(), student.getId());
        assertEquals(awaitQualifikationFromStudent.get().getQualifikationen().get(0).getId(), qualifikation1.getId());
        assertEquals(awaitQualifikationFromStudent.get().getQualifikationen().get(1).getId(), qualifikation2.getId());

        // Delete, Qualifikationen should also be deleted when deleting the student
        studentRepository.deleteById(student.getId());
        assertFalse(studentRepository.existsById(student.getId()));
        assertFalse(qualifikationRepository.existsById(qualifikation1.getId()));
        assertFalse(qualifikationRepository.existsById(qualifikation2.getId()));

    }


}
