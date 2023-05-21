package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
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

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Test
    public void roundTrip() {

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

    @Test
    public void negativTests(){
        assertThrows(Exception.class, () -> kenntnisseRepository.save(null));
        assertThrows(Exception.class, () -> kenntnisseRepository.save(new Kenntnis()));
    }

    @Test
    public void testeStudentKenntnis(){
        // Setup

        // Create User
        Student student = testStudentFactory.createStudent();
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

        testStudentFactory.deleteStudent();
    }
}
