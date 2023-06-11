package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BewerbungTest {
    public static final String VOLLZEIT = "Vollzeit";

    //TODO: Fix this test
    //TODO: Add round trip test for Bewerbung
    //TODO: Test Constraints if student or stellenanzeige gets deleted (cascade) -> Bewerbung gets deleted too
    //TODO: Test add... and remove... methods

    @Autowired
    private BewerbungRepository bewerbungRepository;

    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

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
            .bezeichnung("QTEST_SA1")
            .beschreibung("Nur heute reich werden!!")
            .beschaeftigungsverhaeltnis(VOLLZEIT)
            .start(LocalDate.now())
            .ende(LocalDate.now())
            .unternehmen_stellenanzeigen(unternehmen)
            .beschaeftigungsumfang(VOLLZEIT)
            .build();

    Stellenanzeige stellenanzeige2 = Stellenanzeige.builder()
            .bezeichnung("QTEST_SA2")
            .beschreibung("This is a description for QTEST_SA2.")
            .beschaeftigungsverhaeltnis(VOLLZEIT)
            .start(LocalDate.now())
            .ende(LocalDate.of(2024, 5, 23))
            .beschaeftigungsumfang(VOLLZEIT)
            .unternehmen_stellenanzeigen(unternehmen)
            .build();

    @Test
    public void testRoundTrip() {
        try {
            userRepository.saveAll(Arrays.asList(user1, user3));
            studentRepository.save(student1);
            unternehmenRepository.save(unternehmen);
            stellenanzeigeRepository.save(stellenanzeige1);

            Bewerbung bewerbung = Bewerbung.builder()
                    .student(student1)
                    .stellenanzeige(stellenanzeige1)
                    .build();

            bewerbungRepository.save(bewerbung);

            //Saved in DB?
            assertTrue(bewerbungRepository.existsById(bewerbung.getId()));

            //Read
            Optional<Bewerbung> awaitBewerbung = bewerbungRepository.findById(bewerbung.getId());
            assertTrue(awaitBewerbung.isPresent());
            Bewerbung bewerbungFromDB = awaitBewerbung.get();
            assertEquals(bewerbung.getId(), bewerbungFromDB.getId());

            //Update: zurzeit nichts zu updaten

            //Delete
            bewerbungRepository.deleteById(bewerbung.getId());
            assertFalse(bewerbungRepository.existsById(bewerbung.getId()));

            // Check that Student1 and Stellenanzeige were not deleted
            assertTrue(studentRepository.existsById(student1.getId()));
            assertTrue(stellenanzeigeRepository.existsById(stellenanzeige1.getId()));

            // Delete Rest
            stellenanzeigeRepository.deleteById(stellenanzeige1.getId());
            studentRepository.deleteById(student1.getId());
            unternehmenRepository.deleteById(unternehmen.getId());
            userRepository.deleteAll(Arrays.asList(user1, user3));
        } catch (Exception e) {
            System.out.println("Fehler bei RoundTrip: " + e.getMessage());
        }
    }
    @Test
    public void testStudentBewerbungStellenanzeige() {
        // Setup
        studentRepository.saveAll(Arrays.asList(student1, student2));
        unternehmenRepository.save(unternehmen);
        stellenanzeigeRepository.saveAll(Arrays.asList(stellenanzeige1, stellenanzeige2));

        assertTrue(stellenanzeigeRepository.existsById(stellenanzeige1.getId()));
        assertTrue(stellenanzeigeRepository.existsById(stellenanzeige2.getId()));

        // student1 bewirbt sich auf stellenanzeige1
        Bewerbung bewerbung11 = Bewerbung.builder()
                .student(student1)
                .stellenanzeige(stellenanzeige1)
                .datum(LocalDate.now())
                .build();

        // student1 bewirbt sich auf stellenanzeige2
        Bewerbung bewerbung12 = Bewerbung.builder()
                .student(student1)
                .stellenanzeige(stellenanzeige2)
                .datum(LocalDate.now())
                .build();

        // student2 bewirbt sich auf stellenanzeige1
        Bewerbung bewerbung21 = Bewerbung.builder()
                .student(student2)
                .stellenanzeige(stellenanzeige1)
                .datum(LocalDate.now())
                .build();

        bewerbungRepository.saveAll(Arrays.asList(bewerbung11, bewerbung12, bewerbung21));

        // Add Bewerbungen to Stellenanzeigen
        stellenanzeige1.addBewerbung(bewerbung11);
        stellenanzeige1.addBewerbung(bewerbung21);
        stellenanzeige2.addBewerbung(bewerbung12);


        Optional<Bewerbung> awaitB11 = bewerbungRepository.findById(bewerbung11.getId());
        assertTrue(awaitB11.isPresent());
        Bewerbung b11FromDB = awaitB11.get();
        assertEquals(bewerbung11.getId(), b11FromDB.getId());

        Optional<Bewerbung> awaitB12 = bewerbungRepository.findById(bewerbung12.getId());
        assertTrue(awaitB12.isPresent());
        Bewerbung b12FromDB = awaitB12.get();
        assertEquals(bewerbung12.getId(), b12FromDB.getId());

        Optional<Bewerbung> awaitB21 = bewerbungRepository.findById(bewerbung21.getId());
        assertTrue(awaitB21.isPresent());
        Bewerbung b21FromDB = awaitB21.get();
        assertEquals(bewerbung21.getId(), b21FromDB.getId());

        List<Bewerbung> awaitBewerbungen1 = stellenanzeige1.getBewerbungen();
        assertEquals(b11FromDB.getId(),awaitBewerbungen1.get(0).getId());
        assertEquals(b21FromDB.getId(),awaitBewerbungen1.get(1).getId());

        List<Bewerbung> awaitBewerbungen2 = stellenanzeige2.getBewerbungen();
        assertEquals(b12FromDB.getId(),awaitBewerbungen2.get(0).getId());

        Stellenanzeige awaitStellenanzeigeB11 = b11FromDB.getStellenanzeige();
        Stellenanzeige awaitStellenanzeigeB12 = b12FromDB.getStellenanzeige();

        assertEquals(awaitStellenanzeigeB11.getId(), stellenanzeige1.getId());
        assertEquals(awaitStellenanzeigeB12.getId(), stellenanzeige2.getId());

        // Read

        // Delete

        stellenanzeigeRepository.deleteById(stellenanzeige1.getId());
        assertFalse(stellenanzeigeRepository.existsById(stellenanzeige1.getId()));
        assertFalse(bewerbungRepository.existsById(bewerbung11.getId()));
        assertFalse(bewerbungRepository.existsById(bewerbung21.getId()));
        stellenanzeigeRepository.deleteById(stellenanzeige2.getId());
        assertTrue(studentRepository.existsById(student1.getId()));
        studentRepository.deleteById(student1.getId());
        assertFalse(studentRepository.existsById(student1.getId()));
        assertFalse(bewerbungRepository.existsById(bewerbung12.getId()));

        assertTrue(unternehmenRepository.existsById(unternehmen.getId()));
        unternehmenRepository.deleteById(unternehmen.getId());
        assertFalse(unternehmenRepository.existsById(unternehmen.getId()));


    }
}