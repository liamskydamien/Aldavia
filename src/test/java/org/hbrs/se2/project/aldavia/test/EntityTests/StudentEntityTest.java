package org.hbrs.se2.project.aldavia.test.EntityTests;

import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class StudentEntityTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {

        User testUser = User.builder()
                .userid("TestUserStudent")
                .password("Test")
                .email("Test@Student.de")
                .build();

        student = Student.builder()
                .nachname("Müller")
                .vorname("Julia")
                .matrikelNummer("99999999")
                .studiengang("Informatik")
                .studienbeginn(LocalDate.of(2019, 10, 1))
                .geburtsdatum(LocalDate.of(1999, 10, 1))
                .beschreibung("Ich bin Julia Müller und studiere Informatik an der Hochschule Bonn-Rhein-Sieg.")
                .lebenslauf("Ich habe bereits ein Praktikum bei der Firma Aldavia absolviert.")
                .user(testUser)
                .build();

        studentRepository.save(student);
    }

    @AfterEach
    public void tearDown() {
        studentRepository.delete(student);
    }

    @Test
    public void TestSetupAndTearDown() {
        Optional<Student> studentOptional = studentRepository.findById(student.getStudentId());
        assertTrue(studentOptional.isPresent());
        assertEquals(student, studentOptional.get());
    }
}
