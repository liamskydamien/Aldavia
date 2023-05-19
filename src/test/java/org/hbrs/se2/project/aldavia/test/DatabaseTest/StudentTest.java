package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.K;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;

import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTest {

    @Autowired
    private StudentRepository studentRepository;

    public void testeRoundTrip() {
        try {
            Student student = new Student();
            String id = String.valueOf(student.getUser().getId());
            student.setVorname("Vorname");
            student.setNachname("Nachname");
            student.setMatrikelNummer("12348765");
            student.setStudiengang("TestStudium");
            student.setStudienbeginn(LocalDate.ofEpochDay(2020 - 06 - 01));
            student.setGeburtsdatum(LocalDate.ofEpochDay(2000 - 01 - 01));
            student.setLebenslauf("TestLebenslauf");
            studentRepository.save(student);
            //Saved in DB?
            assertEquals(student, studentRepository.findByUserID(id));

            //Read

            //Update

            //Delete
            studentRepository.deleteByMatrikelNummer("12348765");
        } catch (Exception e) {
            System.out.println("Fehler bei RoundTrip: " + e.getMessage());
        }
    }
}
