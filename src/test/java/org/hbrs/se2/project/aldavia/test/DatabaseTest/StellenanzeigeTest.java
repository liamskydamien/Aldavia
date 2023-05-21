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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
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
            .email("121311324@web.de")
            .userid("Th1o12mas12")
            .password("123")
            .build();

    User u2 = User.builder()
            .email("12345@web.de")
            .userid("Sabrina1")
            .password("1234")
            .build();

    Unternehmen unternehmen1 = Unternehmen.builder()
            .name("Adesso1")
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

       // stellenanzeigeRepository.save(s2);
        //stellenanzeigeRepository.save(s3);


        unternehmen1.addStellenanzeige(s2);
        unternehmen1.addStellenanzeige(s3);


        unternehmenRepository.save(unternehmen1);
        //unternehmen1.setStellenanzeigen(list);
        //unternehmenRepository.save(unternehmen1);
        int idU1 = unternehmen1.getUnternehmenId();
        int idS2 = s2.getStellenanzeigeId();
        int idS3 = s3.getStellenanzeigeId();

        assertEquals(true, stellenanzeigeRepository.existsById(idS2));
        assertEquals(true, stellenanzeigeRepository.existsById(idS3));
        assertEquals(true, unternehmenRepository.existsById(idU1));

        Unternehmen awaitUnternehmen = unternehmenRepository.findById(idU1).get();
        List<Stellenanzeige> awaitStellenanzeigen = awaitUnternehmen.getStellenanzeigen();
        assertEquals(awaitStellenanzeigen.get(0).getStellenanzeigeId(), s2.getStellenanzeigeId());
        assertEquals(awaitStellenanzeigen.get(1).getStellenanzeigeId(), s3.getStellenanzeigeId());


        Stellenanzeige awaitS2 = stellenanzeigeRepository.findById(s2.getStellenanzeigeId()).get();
        Stellenanzeige awaitS3 = stellenanzeigeRepository.findById(s3.getStellenanzeigeId()).get();

        assertEquals(awaitS2.getErsteller().getUnternehmenId(),idU1);
        assertEquals(awaitS3.getErsteller().getUnternehmenId(),idU1);

        unternehmen1.removeStellenanzeige(s3);
        assertEquals(false, stellenanzeigeRepository.existsById(idS3));
        assertEquals(stellenanzeigeRepository.findById(idS3).get().getErsteller(),null);



        unternehmen1.addStellenanzeige(s3);
        unternehmenRepository.deleteById(idU1);
        assertEquals(false, userRepository.existsById(u1.getId()));

        // Stellenanzeigen müssen anscheinden trotz cascade.ALL bei Unternhemen händisch gelöscht werden!
        //stellenanzeigeRepository.deleteById(idS2);
        //stellenanzeigeRepository.deleteById(idS3);
        assertEquals(false, stellenanzeigeRepository.existsById(idS2));
        assertEquals(false, stellenanzeigeRepository.existsById(idS3));
        assertEquals(false, unternehmenRepository.existsById(idU1));

    }
    @Test
    public void testStellenanzeigeVariante1() {
        s1.addUnternehmen(unternehmen1);
        s2.addUnternehmen(unternehmen1);

        stellenanzeigeRepository.save(s1);
        stellenanzeigeRepository.save(s2);
        int idS1 = s1.getStellenanzeigeId();
        int idS2 = s2.getStellenanzeigeId();
        int idU1 = unternehmen1.getUnternehmenId();
        Stellenanzeige awaitS1 = stellenanzeigeRepository.findById(idS1).get();
        Stellenanzeige awaitS2 = stellenanzeigeRepository.findById(idS2).get();


        assertEquals(awaitS1.getErsteller().getUnternehmenId(), unternehmen1.getUnternehmenId());
        assertEquals(awaitS2.getErsteller().getUnternehmenId(), unternehmen1.getUnternehmenId());
        assertEquals(true, unternehmenRepository.existsById(idU1));

        idU1 = awaitS2.getErsteller().getUnternehmenId();

        Unternehmen awaitUnternehmen = unternehmenRepository.findById(idU1).get();
        List<Stellenanzeige> listStellen = awaitUnternehmen.getStellenanzeigen();

        assertEquals(listStellen.get(0).getStellenanzeigeId(), s1.getStellenanzeigeId());
        assertEquals(listStellen.get(1).getStellenanzeigeId(), s2.getStellenanzeigeId());

        //Löschen
        stellenanzeigeRepository.deleteById(idS1);
        stellenanzeigeRepository.deleteById(idS2);


        assertEquals(true, unternehmenRepository.existsById(idU1));
        assertEquals(false, stellenanzeigeRepository.existsById(idS1));
        assertEquals(false, stellenanzeigeRepository.existsById(idS2));

        unternehmenRepository.deleteById(idU1);
        assertEquals(false, unternehmenRepository.existsById(idU1));


    }


    @Test
    public void testStellenanzeigeVariante2() {
        s1.addUnternehmen(unternehmen1);
        s2.addUnternehmen(unternehmen1);

        stellenanzeigeRepository.save(s1);
        stellenanzeigeRepository.save(s2);
        int idS1 = s1.getStellenanzeigeId();
        int idS2 = s2.getStellenanzeigeId();
        int idU1 = unternehmen1.getUnternehmenId();
        Stellenanzeige awaitS1 = stellenanzeigeRepository.findById(idS1).get();
        Stellenanzeige awaitS2 = stellenanzeigeRepository.findById(idS2).get();


        assertEquals(awaitS1.getErsteller().getUnternehmenId(), unternehmen1.getUnternehmenId());
        assertEquals(awaitS2.getErsteller().getUnternehmenId(), unternehmen1.getUnternehmenId());
        assertTrue(unternehmenRepository.existsById(idU1));

        int idU12 = awaitS2.getErsteller().getUnternehmenId();

        //Löschen
        stellenanzeigeRepository.deleteById(idS1);



        assertEquals(false, stellenanzeigeRepository.existsById(idS1));
        assertEquals(false, stellenanzeigeRepository.existsById(idS2));
        assertEquals(false, unternehmenRepository.existsById(idU12));




    }
  /*  @Test
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

   */
}
