package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.hibernate.loader.hql.QueryLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QualifikationTest {

    @Autowired
    private QualifikationRepository qualifikationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Test
    public void roundTripTest(){
        // Test create
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBereich("Qualitätsmanagement");
        qualifikation.setBezeichnung("Praktikum in Software-Testing");
        qualifikation.setBeschreibung("Ich bin zustaendig fuer das Testen von Software");
        qualifikation.setBeschaeftigungsart("Praktikum");
        qualifikationRepository.save(qualifikation);
        int qualifikationId = qualifikation.getId();

        // Test Read
        Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(qualifikationId);
        assertTrue(awaitQualifikation.isPresent());
        assertEquals(qualifikation, awaitQualifikation.get());

        // Test Update
        qualifikation.setBezeichnung("Testmanagement");
        qualifikationRepository.save(qualifikation);

        Optional<Qualifikation> awaitNewQualifikation = qualifikationRepository.findById(qualifikationId);
        assertTrue(awaitNewQualifikation.isPresent());
        assertEquals(qualifikation, awaitNewQualifikation.get());

        // Test Delete
        qualifikationRepository.deleteById(qualifikationId);
        assertFalse(qualifikationRepository.existsById(qualifikationId));
    }

    @Test
    public void negativeTests(){
        assertThrows(Exception.class, () -> {
            qualifikationRepository.save(null);
        });
        assertThrows(Exception.class, () -> {
            qualifikationRepository.deleteById(-1);
        });
    }

    @Test
    public void testStudentQualifikations(){
        // Setup

        Student student = testStudentFactory.createStudent();

        int studentId = student.getStudentId();

        List<Student> students = new ArrayList<>();
        students.add(student);

        // Create Qualifikation
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBereich("Qualitätsmanagement");
        qualifikation.setBezeichnung("Praktikum in Software-Testing");
        qualifikation.setBeschreibung("Ich bin zustaendig fuer das Testen von Software");
        qualifikation.setBeschaeftigungsart("Praktikum");
        qualifikation.setStudenten(students);
        qualifikationRepository.save(qualifikation);
        int qualifikationId = qualifikation.getId();

        List<Qualifikation> qualifikations = new ArrayList<>();
        qualifikations.add(qualifikation);
        student.setQualifikationen(qualifikations);
        studentRepository.save(student);

        // Test Read
        Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(qualifikationId);
        assertTrue(awaitQualifikation.isPresent());
        assertEquals(qualifikation.getStudenten().get(0).getStudentId(), awaitQualifikation.get().getStudenten().get(0).getStudentId());

        // Test Delete
        student.setQualifikationen(null);
        studentRepository.save(student);
        qualifikationRepository.deleteById(qualifikationId);
        assertFalse(qualifikationRepository.existsById(qualifikationId));

        // Delete Student
        testStudentFactory.deleteStudent();
    }
}
