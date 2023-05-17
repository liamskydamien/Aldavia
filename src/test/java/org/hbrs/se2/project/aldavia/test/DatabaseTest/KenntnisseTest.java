package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KenntnisseTest {

    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testeRoundTrip() {
        try {
            Kenntnis kenntnis = new Kenntnis();
            kenntnis.setBezeichnung("Java_Test");
            kenntnisseRepository.save(kenntnis);
            //Saved in DB?
            assertTrue(kenntnisseRepository.existsById("Java_Test"));

            //Read
            Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById("Java_Test");
            assertTrue(awaitKenntnis.isPresent());
            Kenntnis kenntnisFromDB = awaitKenntnis.get();
            assertEquals("Java_Test", kenntnisFromDB.getBezeichnung());

            //Update
            kenntnisFromDB.setBezeichnung("Java_Test_8");
            kenntnisseRepository.save(kenntnisFromDB);
            awaitKenntnis = kenntnisseRepository.findById("Java_Test_8");
            assertTrue(awaitKenntnis.isPresent());
            kenntnisFromDB = awaitKenntnis.get();
            assertEquals("Java_Test_8", kenntnisFromDB.getBezeichnung());

            //Delete
            kenntnisseRepository.deleteById("Java_Test_8");
            assertFalse(kenntnisseRepository.existsById("Java_Test_8"));
        }
        catch (Exception e) {
            System.out.println("Fehler bei RoundTrip: " + e.getMessage());
        }
    }

    @Test
    public void negativTests(){
        Kenntnis kenntnis = new Kenntnis();
        kenntnis.setBezeichnung("Java_Test");
        kenntnisseRepository.save(kenntnis);
        //Saved in DB?
        assertTrue(kenntnisseRepository.existsById("Java_Test"));

        Kenntnis doppelteKenntnis = new Kenntnis();
        kenntnis.setBezeichnung("Java_Test");
        assertThrows(Exception.class, () -> kenntnisseRepository.save(doppelteKenntnis));
        assertThrows(Exception.class, () -> kenntnisseRepository.save(null));
        assertThrows(Exception.class, () -> kenntnisseRepository.save(new Kenntnis()));

        kenntnisseRepository.deleteById("Java_Test");
    }

    @Test
    public void testeStudentKenntnis(){
        // Setup

        // Create User
        User user = new User();
        user.setUserid("test_user3");
        user.setPassword("test_user3");
        user.setEmail("test@test_user3.de");
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

        // Create Qualifikation
        Kenntnis kenntnis = new Kenntnis();
        kenntnis.setBezeichnung("Java_Test");
        kenntnis.setStudenten(students);
        kenntnisseRepository.save(kenntnis);
        String  kenntnisId = "Java_Test";

        List<Kenntnis> kenntnisse = new ArrayList<>();
        kenntnisse.add(kenntnis);
        student.setKenntnisse(kenntnisse);
        studentRepository.save(student);

        // Read
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisId);
        assertTrue(awaitKenntnis.isPresent());
        Kenntnis kenntnisFromDB = awaitKenntnis.get();
        assertEquals("Java_Test", kenntnisFromDB.getBezeichnung());
        assertEquals(1, kenntnisFromDB.getStudenten().size());
        assertEquals(student.getStudentId(), kenntnisFromDB.getStudenten().get(0).getStudentId());

        // Delete
        student.setKenntnisse(null);
        studentRepository.save(student);

        kenntnisseRepository.deleteById(kenntnisId);
        assertFalse(kenntnisseRepository.existsById(kenntnisId));

        studentRepository.deleteById(studentId);
        assertFalse(studentRepository.existsById(studentId));

        userRepository.deleteById(userId);
        assertFalse(userRepository.existsById(userId));
    }
}
