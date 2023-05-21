package org.hbrs.se2.project.aldavia.test.DatabaseTest;

//import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
//import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.BewerbungRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BewerbungTest {

    //TODO: Fix this test
    //TODO: Add round trip test for Bewerbung
    //TODO: Test Constraints if student or stellenanzeige gets deleted (cascade) -> Bewerbung gets deleted too
    //TODO: Test add... and remove... methods
    /*

    @Autowired
    private BewerbungRepository bewerbungRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private StellenanzeigeRepository stellenanzeigeRepository;

    @Test
    public void testeRoundTrip() {
        try {
            Bewerbung bewerbung = new Bewerbung();
            bewerbung.setDatum(LocalDate.of(2023, 5, 17));
            bewerbungRepository.save(bewerbung);
            int id = bewerbung.getBewerbungId();
            //Saved in DB?
//            assertEquals(bewerbung, bewerbungRepository.findByBewerbungID(id));

            //Read
            Optional<Bewerbung> awaitBewerbung = bewerbungRepository.findById(id);
            assertTrue(awaitBewerbung.isPresent());
            Bewerbung bewerbungFromDB = awaitBewerbung.get();
            assertEquals(id, bewerbungFromDB.getBewerbungId());

            //Update
            LocalDate newDate = LocalDate.of(2022, 5, 17);
            bewerbungFromDB.setDatum(newDate);
            bewerbungRepository.save(bewerbungFromDB);
            assertEquals(newDate, bewerbungFromDB.getDatum());

            //Delete
            bewerbungRepository.deleteById(id);
            assertFalse(bewerbungRepository.existsById(id));
        }
        catch (Exception e) {
            System.out.println("Fehler bei RoundTrip: " + e.getMessage());
        }
    }


    @Test
    public void testNullabilityOfRequiredFields() {
        // Test null student
        assertThrows(NullPointerException.class, () -> {
            Bewerbung bewerbung = new Bewerbung();
            bewerbung.setDatum(LocalDate.now());
            bewerbung.setStudent(null);
        });

        // Test null stellenanzeige
        assertThrows(NullPointerException.class, () -> {
            Bewerbung bewerbung = new Bewerbung();
            bewerbung.setDatum(LocalDate.now());
            bewerbung.setStudent(new Student());
            bewerbung.setStellenanzeige(null);
        });
    }

    @Test
    public void testUniquenessOfBewerbungId() {
        Bewerbung bewerbung1 = new Bewerbung();
        bewerbung1.setDatum(LocalDate.now());
        bewerbungRepository.save(bewerbung1);

        Bewerbung bewerbung2 = new Bewerbung();
        bewerbung2.setDatum(LocalDate.now());
        bewerbungRepository.save(bewerbung2);

        assertNotEquals(bewerbung1.getBewerbungId(), bewerbung2.getBewerbungId());
    }

    @Test
    public void testEqualityAndHashCode() {
        Bewerbung bewerbung1 = new Bewerbung();
        bewerbung1.setBewerbungId(1);
        bewerbung1.setDatum(LocalDate.now());

        Bewerbung bewerbung2 = new Bewerbung();
        bewerbung2.setBewerbungId(1);
        bewerbung2.setDatum(LocalDate.now());

        assertEquals(bewerbung1, bewerbung2);
        assertEquals(bewerbung1.hashCode(), bewerbung2.hashCode());

        Bewerbung bewerbung3 = new Bewerbung();
        bewerbung3.setBewerbungId(2);
        bewerbung3.setDatum(LocalDate.now());

        assertNotEquals(bewerbung1, bewerbung3);
        assertNotEquals(bewerbung1.hashCode(), bewerbung3.hashCode());
    }

    @Test
    public void testeStudentBewerbung(){
        // Setup

        // Create User
        User user = new User();
        user.setUserid("test_user12");
        user.setPassword("test_user12");
        user.setEmail("test12@test_user.de");
        userRepository.save(user);
        int userId = user.getId();

        // Create Student
        Student student = new Student();
        student.setVorname("Guido");
        student.setNachname("Müller");
        student.setMatrikelNummer("123456789012");
        Optional<User> userOptional = userRepository.findById(userId);
        assertTrue(userOptional.isPresent());
        student.setUser(userOptional.get());
        studentRepository.save(student);
        int studentId = student.getStudentId();

        // Create Bewerbung
        Bewerbung bewerbung = new Bewerbung();
        bewerbung.setStudent(student);
        bewerbungRepository.save(bewerbung);
        int bewerbungId = bewerbung.getBewerbungId();

        List<Bewerbung> bewerbungen = new ArrayList<>();
        bewerbungen.add(bewerbung);
        student.setBewerbungen(bewerbungen);
        studentRepository.save(student);

        // Read
        Optional<Bewerbung> awaitBewerbung = bewerbungRepository.findById(bewerbungId);
        assertTrue(awaitBewerbung.isPresent());
        Bewerbung bewerbungFromDB = awaitBewerbung.get();
        assertEquals(bewerbungId, bewerbungFromDB.getBewerbungId());
        assertEquals(studentId, bewerbungFromDB.getStudent().getStudentId());

        // Delete
        student.setBewerbungen(null);
        studentRepository.save(student);

        bewerbungRepository.deleteById(bewerbungId);
        assertFalse(bewerbungRepository.existsById(bewerbungId));

        studentRepository.deleteById(studentId);
        assertFalse(studentRepository.existsById(studentId));

        userRepository.deleteById(userId);
        assertFalse(userRepository.existsById(userId));
    }

//    @Test
//    public void testeStellenanzeigeBewerbung(){
//        // Setup
//
//        // Create Stellenanzeige
//        Stellenanzeige stellenanzeige = new Stellenanzeige();
//        stellenanzeige.setBezeichnung("TEST_SA");
//        stellenanzeigeRepository.save(stellenanzeige);
//        int stellenanzeigeId = stellenanzeige.getStellenanzeigeId();
//
//        // Create Bewerbung
//        Bewerbung bewerbung = new Bewerbung();
//        bewerbung.setStellenanzeige(stellenanzeige);
//        bewerbungRepository.save(bewerbung);
//        String bewerbungId = String.valueOf(bewerbung.getBewerbungId());
//
//        List<Bewerbung> bewerbungen = new ArrayList<>();
//        bewerbungen.add(bewerbung);
//        stellenanzeige.setBewerbungen(bewerbungen);
//        stellenanzeigeRepository.save(bewerbung);
//
//        // Read
//        Optional<Bewerbung> awaitBewerbung = bewerbungRepository.findById(bewerbungId);
//        assertTrue(awaitBewerbung.isPresent());
//        Bewerbung bewerbungFromDB = awaitBewerbung.get();
//        assertEquals(bewerbungId, bewerbungFromDB.getBewerbungId());
//        assertEquals(stellenanzeigeId, bewerbungFromDB.getStellenanzeige().getStellenanzeigeId());
//
//        // Delete
//        stellenanzeige.setBewerbungen(null);
//        stellenanzeigeRepository.save(stellenanzeige);
//
//        bewerbungRepository.deleteById(bewerbungId);
//        assertFalse(bewerbungRepository.existsById(bewerbungId));
//
//        stellenanzeigeRepository.deleteById(stellenanzeigeId);
//        assertFalse(stellenanzeigeRepository.existsById(stellenanzeigeId));
//    }

     */

}
