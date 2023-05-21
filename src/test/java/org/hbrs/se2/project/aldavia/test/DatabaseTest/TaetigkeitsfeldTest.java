package org.hbrs.se2.project.aldavia.test.DatabaseTest;

//import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
//import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaetigkeitsfeldTest {
    /*

    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private StellenanzeigeRepository stellenanzeigeRepository;


    @Test
    public void roundTrip() {
        String bezeichnung = "IT";
        Taetigkeitsfeld taetigkeitsfeld = new Taetigkeitsfeld();
        taetigkeitsfeld.setBezeichnung(bezeichnung);
        taetigkeitsfeldRepository.save(taetigkeitsfeld); //Saved in DB?
        assertTrue(taetigkeitsfeldRepository.existsById(bezeichnung));

        //Read
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(bezeichnung);
        assertTrue(awaitTaetigkeitsfeld.isPresent());
        Taetigkeitsfeld taetigkeitsfeldFromDB = awaitTaetigkeitsfeld.get();
        assertEquals(bezeichnung, taetigkeitsfeldFromDB.getBezeichnung());

        //Update
        String newBezeichnung = "Informatik";
        taetigkeitsfeldFromDB.setBezeichnung(newBezeichnung);
        taetigkeitsfeldRepository.save(taetigkeitsfeldFromDB);
        awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(newBezeichnung);
        assertTrue(awaitTaetigkeitsfeld.isPresent());
        taetigkeitsfeldFromDB = awaitTaetigkeitsfeld.get();
        assertEquals(newBezeichnung, taetigkeitsfeldFromDB.getBezeichnung());

        //Delete
        taetigkeitsfeldRepository.deleteById(newBezeichnung);
        assertFalse(taetigkeitsfeldRepository.existsById(newBezeichnung));
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
        //assertThrows(Exception.class, () -> taetigkeitsfeldRepository.save(null));
        assertThrows(Exception.class, () -> taetigkeitsfeldRepository.save(new Taetigkeitsfeld()));

//        taetigkeitsfeldRepository.deleteById("Java_Test");
    }

    @Test
    @Transactional
    public void testeStudentTaetigkeitsfeld(){
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
        student.setMatrikelNummer("12345678901");
        Optional<User> userOptional = userRepository.findById(userId);
        assertTrue(userOptional.isPresent());
        student.setUser(userOptional.get());
        studentRepository.save(student);
        int studentId = student.getStudentId();

        List<Student> students = new ArrayList<>();
        students.add(student);

        // Create Taetigkeitsfeld
        Taetigkeitsfeld taetigkeitsfeld = new Taetigkeitsfeld();
        taetigkeitsfeld.setBezeichnung("Java_Test");
        taetigkeitsfeld.setStudenten(students);
        taetigkeitsfeldRepository.save(taetigkeitsfeld);
        String taetigkeitsfeldId = "Java_Test";

        List<Taetigkeitsfeld> taetigkeitsfeldser = new ArrayList<>();
        taetigkeitsfeldser.add(taetigkeitsfeld);
        student.setTaetigkeitsfelder(taetigkeitsfeldser);
        studentRepository.save(student);

        // Read
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldId);
        assertTrue(awaitTaetigkeitsfeld.isPresent());
        Taetigkeitsfeld taetigkeitsfeldFromDB = awaitTaetigkeitsfeld.get();
        assertEquals("Java_Test", taetigkeitsfeldFromDB.getBezeichnung());
        assertEquals(1, taetigkeitsfeldFromDB.getStudenten().size());
        assertEquals(student.getStudentId(), taetigkeitsfeldFromDB.getStudenten().get(0).getStudentId());

        // Delete
        student.setTaetigkeitsfelder(null);
        studentRepository.save(student);

        taetigkeitsfeldRepository.deleteById(taetigkeitsfeldId);
        assertFalse(taetigkeitsfeldRepository.existsById(taetigkeitsfeldId));

        studentRepository.deleteById(studentId);
        assertFalse(studentRepository.existsById(studentId));

        userRepository.deleteById(userId);
        assertFalse(userRepository.existsById(userId));
    }

//    @Test
//    @Transactional
//    public void testeStellenanzeigeTaetigkeitsfeld(){
//        // Setup
//
//        // Create Stellenanzeige
//        Stellenanzeige stellenanzeige = new Stellenanzeige();
//        stellenanzeige.setBezeichnung("TEST_SA");
//        stellenanzeigeRepository.save(stellenanzeige);
//        int stellenanzeigeId = stellenanzeige.getStellenanzeigeId();
//
//        List<Stellenanzeige> stellenanzeigen = new ArrayList<>();
//        stellenanzeigen.add(stellenanzeige);
//
//        // Create Taetigkeitsfeld
//        Taetigkeitsfeld taetigkeitsfeld = new Taetigkeitsfeld();
//        taetigkeitsfeld.setBezeichnung("Java_Test");
//        taetigkeitsfeld.setStellenanzeigen(stellenanzeigen);
//        taetigkeitsfeldRepository.save(taetigkeitsfeld);
//        String taetigkeitsfeldId = "Java_Test";
//
//        List<Taetigkeitsfeld> taetigkeitsfeldser = new ArrayList<>();
//        taetigkeitsfeldser.add(taetigkeitsfeld);
//        stellenanzeige.setTaetigkeitsfelder(taetigkeitsfeldser);
//        stellenanzeigeRepository.save(stellenanzeige);
//
//        // Read
//        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldId);
//        assertTrue(awaitTaetigkeitsfeld.isPresent());
//        Taetigkeitsfeld taetigkeitsfeldFromDB = awaitTaetigkeitsfeld.get();
//        assertEquals("Java_Test", taetigkeitsfeldFromDB.getBezeichnung());
//        assertEquals(1, taetigkeitsfeldFromDB.getStellenanzeigen().size());
//        assertEquals(stellenanzeigeId, taetigkeitsfeldFromDB.getStellenanzeigen().get(0).getStellenanzeigeId());
//
//        // Delete
//        stellenanzeige.setTaetigkeitsfelder(null);
//        stellenanzeigeRepository.save(stellenanzeige);
//
//        taetigkeitsfeldRepository.deleteById(taetigkeitsfeldId);
//        assertFalse(taetigkeitsfeldRepository.existsById(taetigkeitsfeldId));
//
//        stellenanzeigeRepository.deleteById(stellenanzeigeId);
//        assertFalse(stellenanzeigeRepository.existsById(stellenanzeigeId));
//    }
*/
}
