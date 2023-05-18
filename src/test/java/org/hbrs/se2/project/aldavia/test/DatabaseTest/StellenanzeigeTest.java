package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.checkerframework.checker.units.qual.A;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StellenanzeigeTest {
    @Autowired
    StellenanzeigeRepository stellenanzeigeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UnternehmenRepository unternehmenRepository;

    @Autowired
    StudentRepository studentRepository;

    Stellenanzeige s1 = Stellenanzeige.builder()
            .bezeichnung("Java Praktikum")
            .beschreibung("Hier lernst du professionell Java.")
            .bezahlung("12,5€")
            .build();

    Stellenanzeige s2 = Stellenanzeige.builder()
            .bezeichnung("Tätigkeit RE")
            .beschreibung("Hier lernst du professionell RE.")
            .bezahlung("20,5€")
            .build();

    Stellenanzeige s3 = Stellenanzeige.builder()
            .bezeichnung("C++ Praktikum")
            .beschreibung("Hier lernst du professionell C++.")
            .bezahlung("18,5€")
            .build();

    User u1 = User.builder()
            .email("123@web.de")
            .userid("Thomas")
            .password("123")
            .build();

    User u2 = User.builder()
            .email("1234@web.de")
            .userid("Sabrina")
            .password("1234")
            .build();

    Unternehmen unternehmen1 = Unternehmen.builder()
            .name("Adesso")
            .beschreibung("IT-Unternehmen")
            .user(u1)
            .build();

    Student student1 = Student.builder()
            .nachname("Müller")
            .vorname("Sabrina")
            .user(u2)
            .matrikelNummer("123")
            .build();
    @Test
    public void roundTrip() {
        //Create
        stellenanzeigeRepository.save(s1);
        int idS1 = s1.getStellenanzeigeId();
        Stellenanzeige s1Test = stellenanzeigeRepository.findById(idS1).get();

        assertEquals(s1.getStellenanzeigeId(), s1Test.getStellenanzeigeId());

        //Update
        s1.setBezeichnung("Java Job");
        stellenanzeigeRepository.save(s1);
        s1Test = stellenanzeigeRepository.findById(idS1).get();

        assertEquals(s1.getBezeichnung(),s1Test.getBezeichnung());

        //Delete
        stellenanzeigeRepository.deleteById(idS1);
        assertEquals(false, stellenanzeigeRepository.existsById(idS1));

    }

    @Test
    public void testErstellen() {

        unternehmen1.setStellenanzeigen(List.of(s2,s3));
        unternehmenRepository.save(unternehmen1);
        int idU1 = unternehmen1.getUnternehmenId();
        int idS2 = s2.getStellenanzeigeId();
        int idS3 = s3.getStellenanzeigeId();

        assertEquals(true, stellenanzeigeRepository.existsById(idS2));
        assertEquals(true, stellenanzeigeRepository.existsById(idS3));
        assertEquals(true, unternehmenRepository.existsById(idU1));

        unternehmenRepository.deleteById(idU1);

        // Stellenanzeigen müssen anscheinden trotz cascade.ALL bei Unternhemen händisch gelöscht werden!
        stellenanzeigeRepository.deleteById(idS2);
        stellenanzeigeRepository.deleteById(idS3);



        assertEquals(false, unternehmenRepository.existsById(idU1));
        assertEquals(false, stellenanzeigeRepository.existsById(idS2));
        assertEquals(false, stellenanzeigeRepository.existsById(idS3));

    }
    @Test
    public void testFavourisiert() {
        student1.setStellenanzeigenFavourisiert(List.of(s2,s3));
        int idStud1 = student1.getStudentId();

        studentRepository.save(student1);
        Student student1Test = studentRepository.findById(idStud1).get();
        assertEquals(student1.getStudentId(), student1Test.getStudentId());

        assertEquals(true, stellenanzeigeRepository.existsById(s2.getStellenanzeigeId()));
        assertEquals(true, stellenanzeigeRepository.existsById(s2.getStellenanzeigeId()));


    }
    @Test
    public void testTaetigkeiten() {

    }
}
