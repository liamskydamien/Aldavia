package org.hbrs.se2.project.aldavia.test.DatabaseTest;


import org.hbrs.se2.project.aldavia.repository.*;
import org.hbrs.se2.project.aldavia.entities.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//import java.time.LocalDate;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class TaetigkeitsfeldTest {

    public static final String BESCHAEFTIGUNGSVERHAELTNIS = "Vollzeit";
    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private StudentRepository studentRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    User user1 = User.builder()
            .email("Student1@qtest.vn")
            .userid("QStudent1")
            .password("qwedfghbn")
            .build();

    User user2 = User.builder()
            .email("Student2@qtest.vn")
            .userid("QStudent2")
            .password("qwsdfghjk")
            .build();

    User user3 = User.builder()
            .email("QtestAG@qtest.vn")
            .userid("QtestAG")
            .password("asdfvgbhnj")
            .build();

    Student student1 = Student.builder()
            .nachname("Nguyen")
            .vorname("Qtest1")
            .user(user1)
            .matrikelNummer("963852")
            .build();

    Student student2 = Student.builder()
            .nachname("Nguyen")
            .vorname("Qtest2")
            .user(user2)
            .matrikelNummer("936258")
            .build();

    Unternehmen unternehmen = Unternehmen.builder()
            .name("QTest AG")
            .user(user3)
            .build();

    Stellenanzeige stellenanzeige1 = Stellenanzeige.builder()
            .bezeichnung("QTEST_SA")
            .beschreibung("Nur heute reich werden!!")
            .beschaeftigungsverhaeltnis(BESCHAEFTIGUNGSVERHAELTNIS)
            .start(LocalDate.now())
            .ende(LocalDate.now())
            .unternehmen_stellenanzeigen(unternehmen)
            .beschaeftigungsumfang(BESCHAEFTIGUNGSVERHAELTNIS)
            .build();

    Stellenanzeige stellenanzeige2 = Stellenanzeige.builder()
            .bezeichnung("QTEST_SA2")
            .beschreibung("This is a description for QTEST_SA2.")
            .beschaeftigungsverhaeltnis(BESCHAEFTIGUNGSVERHAELTNIS)
            .start(LocalDate.now())
            .ende(LocalDate.of(2024, 5, 23))
            .beschaeftigungsumfang(BESCHAEFTIGUNGSVERHAELTNIS)
            .unternehmen_stellenanzeigen(unternehmen)
            .build();

    Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
            .bezeichnung("IT-Dienstleister")
            .build();

    @Test
    public void testRoundTrip() {
        try {
            // Save the initial entity
            Taetigkeitsfeld savedTaetigkeitsfeld = taetigkeitsfeldRepository.save(taetigkeitsfeld);
            String originalBezeichnung = savedTaetigkeitsfeld.getBezeichnung();

            // Verify if the entity is saved in the database
            assertTrue(taetigkeitsfeldRepository.existsById(originalBezeichnung));

            // Read the entity from the database
            Optional<Taetigkeitsfeld> taetigkeitsfeldOptional = taetigkeitsfeldRepository.findById(originalBezeichnung);
            assertTrue(taetigkeitsfeldOptional.isPresent());
            Taetigkeitsfeld taetigkeitsfeldFromDB = taetigkeitsfeldOptional.get();
            assertEquals(originalBezeichnung, taetigkeitsfeldFromDB.getBezeichnung());

//            // Update the entity
//            String newBezeichnung = "Informatik";
//            taetigkeitsfeldFromDB.setBezeichnung(newBezeichnung);
//            taetigkeitsfeldRepository.save(taetigkeitsfeldFromDB);
//
//            // Verify if the entity is updated
//            Optional<Taetigkeitsfeld> updatedTaetigkeitsfeldOptional = taetigkeitsfeldRepository.findById(newBezeichnung);
//            assertTrue(updatedTaetigkeitsfeldOptional.isPresent());
//            Taetigkeitsfeld retrievedTaetigkeitsfeld = updatedTaetigkeitsfeldOptional.get();

//            // Verify if the original entity is deleted
//            assertFalse(taetigkeitsfeldRepository.existsById(originalBezeichnung));
//
//            // Verify the updated entity
//            assertEquals(newBezeichnung, retrievedTaetigkeitsfeld.getBezeichnung());

            // Delete the entity
            taetigkeitsfeldRepository.deleteById(originalBezeichnung);

            // Verify if the entity is deleted
            assertFalse(taetigkeitsfeldRepository.existsById(originalBezeichnung));
        } catch (Exception e) {
            System.out.println("Fehler bei RoundTrip: " + e.getMessage());
        }
    }

    @Test
    public void negativTests(){
//        Taetigkeitsfeld taetigkeitsfeld1 = new Taetigkeitsfeld();
//        taetigkeitsfeld1.setBezeichnung("Java_Test");
//        taetigkeitsfeldRepository.save(taetigkeitsfeld1);
//        //Saved in DB?
//        assertTrue(taetigkeitsfeldRepository.existsById("Java_Test"));
//
//        Taetigkeitsfeld taetigkeitsfeld2 = new Taetigkeitsfeld();
//        taetigkeitsfeld2.setBezeichnung("Java_Test");
        //assertThrows(Exception.class, () -> taetigkeitsfeldRepository.save(taetigkeitsfeld2));
        assertThrows(Exception.class, () -> taetigkeitsfeldRepository.save(null));
        assertThrows(Exception.class, () -> taetigkeitsfeldRepository.save(new Taetigkeitsfeld()));

//        taetigkeitsfeldRepository.deleteById("Java_Test");
    }

    @Test
    public void testStudent(){
        // Setup
        studentRepository.saveAll(Arrays.asList(student1, student2));

        Optional<Student> awaitS1 = studentRepository.findById(student1.getId());
        assertTrue(awaitS1.isPresent());
        Student s1FromDB = awaitS1.get();
        assertEquals(student1.getId(), s1FromDB.getId());

        Optional<Student> awaitS2 = studentRepository.findById(student2.getId());
        assertTrue(awaitS2.isPresent());
        Student s2FromDB = awaitS2.get();
        assertEquals(student2.getId(), s2FromDB.getId());

        taetigkeitsfeld.addStudent(student1);
        taetigkeitsfeld.addStudent(student2);

        taetigkeitsfeldRepository.save(taetigkeitsfeld);
        Optional<Taetigkeitsfeld> awaitT = taetigkeitsfeldRepository.findById(taetigkeitsfeld.getBezeichnung());
        assertTrue(awaitT.isPresent());
        Taetigkeitsfeld taetigkeitsfeldFromDB = awaitT.get();
        assertEquals(taetigkeitsfeld.getBezeichnung(), taetigkeitsfeldFromDB.getBezeichnung());

        //Referenzen Prüfen
        List<Student> awaitStudents = taetigkeitsfeld.getStudents();
        assertEquals(awaitStudents.get(0), s1FromDB);
        assertEquals(awaitStudents.get(1), s2FromDB);

        List<Taetigkeitsfeld> awaitTaetigkeitsfeldS1 = s1FromDB.getTaetigkeitsfelder();
        List<Taetigkeitsfeld> awaitTaetigkeitsfeldS2 = s2FromDB.getTaetigkeitsfelder();

        assertEquals(awaitTaetigkeitsfeldS1.get(0),taetigkeitsfeld);
        assertEquals(awaitTaetigkeitsfeldS2.get(0),taetigkeitsfeld);

        student1.removeTaetigkeitsfeld(taetigkeitsfeld);
        student2.removeTaetigkeitsfeld(taetigkeitsfeld);
        //Löschen
        taetigkeitsfeldRepository.deleteById(taetigkeitsfeld.getBezeichnung());
        //Students dürfen nicht mit gelöscht sein
        assertTrue(studentRepository.existsById(student1.getId()));
        assertTrue(studentRepository.existsById(student2.getId()));
        studentRepository.deleteById(student1.getId());
        studentRepository.deleteById(student2.getId());
        assertFalse(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));
        assertFalse(studentRepository.existsById(student1.getId()));
        assertFalse(studentRepository.existsById(student2.getId()));
    }

    @Test
    public void testStellenanzeige(){
        unternehmenRepository.save(unternehmen);
        stellenanzeigeRepository.save(stellenanzeige1);
        stellenanzeigeRepository.save(stellenanzeige2);


        Optional<Stellenanzeige> awaitStellenanzeige1 = stellenanzeigeRepository.findById(stellenanzeige1.getId());
        assertTrue(awaitStellenanzeige1.isPresent());
        Stellenanzeige stellenanzeige1FromDB = awaitStellenanzeige1.get();
        assertEquals(stellenanzeige1.getId(), stellenanzeige1FromDB.getId());

        Optional<Stellenanzeige> awaitStellenanzeige2 = stellenanzeigeRepository.findById(stellenanzeige2.getId());
        assertTrue(awaitStellenanzeige2.isPresent());
        Stellenanzeige stellenanzeige2FromDB = awaitStellenanzeige2.get();
        assertEquals(stellenanzeige2.getId(), stellenanzeige2FromDB.getId());

        taetigkeitsfeld.addStellenanzeige(stellenanzeige1);
        taetigkeitsfeld.addStellenanzeige(stellenanzeige2);

        taetigkeitsfeldRepository.save(taetigkeitsfeld);
        Optional<Taetigkeitsfeld> awaitT = taetigkeitsfeldRepository.findById(taetigkeitsfeld.getBezeichnung());
        assertTrue(awaitT.isPresent());
        Taetigkeitsfeld taetigkeitsfeldFromDB = awaitT.get();
        assertEquals(taetigkeitsfeld.getBezeichnung(), taetigkeitsfeldFromDB.getBezeichnung());

        //Referenzen Prüfen
        List<Stellenanzeige> awaitStellenanzeigen = taetigkeitsfeld.getStellenanzeigen();
        assertEquals(awaitStellenanzeigen.get(0), stellenanzeige1FromDB);
        assertEquals(awaitStellenanzeigen.get(1), stellenanzeige2FromDB);

        List<Taetigkeitsfeld> awaitTaetigkeitsfeldS1 = stellenanzeige1FromDB.getTaetigkeitsfelder();
        List<Taetigkeitsfeld> awaitTaetigkeitsfeldS2 = stellenanzeige2FromDB.getTaetigkeitsfelder();

        assertEquals(awaitTaetigkeitsfeldS1.get(0),taetigkeitsfeld);
        assertEquals(awaitTaetigkeitsfeldS2.get(0),taetigkeitsfeld);

        stellenanzeige1.removeTaetigkeitsfeld(taetigkeitsfeld);
        stellenanzeige2.removeTaetigkeitsfeld(taetigkeitsfeld);

        //Löschens
        taetigkeitsfeldRepository.deleteById(taetigkeitsfeld.getBezeichnung());
        //Stellenanzeigen dürfen nicht mit gelöscht sein
        assertTrue(stellenanzeigeRepository.existsById(stellenanzeige1.getId()));
        assertTrue(stellenanzeigeRepository.existsById(stellenanzeige2.getId()));
        stellenanzeigeRepository.deleteById(stellenanzeige1.getId());
        stellenanzeigeRepository.deleteById(stellenanzeige2.getId());
        assertFalse(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));
        assertFalse(stellenanzeigeRepository.existsById(stellenanzeige1.getId()));
        assertFalse(stellenanzeigeRepository.existsById(stellenanzeige2.getId()));
    }

}
