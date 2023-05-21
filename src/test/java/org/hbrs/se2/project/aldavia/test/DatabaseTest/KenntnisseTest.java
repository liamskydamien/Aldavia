package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class KenntnisseTest {
    //TODO: Fix this test
    //TODO: Add round trip test for Kenntnisse
    //TODO: Test Constraints if student gets deleted (cascade) -> Kenntnisse should not get deleted too
    //TODO: Test add... and remove... methods

    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    @Autowired
    private StudentRepository studentRepository;

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
        // Da Kenntnisse nicht verändert werden können/sollen, wird hier nichts getestet

        //Delete
        kenntnisseRepository.deleteById("Java_Test");
        assertFalse(kenntnisseRepository.existsById("Java_Test"));
    }

    @Test
    public void negativTests(){
        assertThrows(Exception.class, () -> kenntnisseRepository.save(null));
        assertThrows(Exception.class, () -> kenntnisseRepository.save(new Kenntnis()));
    }

    @Test
    public void testAddKenntnisToStudent(){
        Kenntnis kenntnis = Kenntnis.builder()
                .bezeichnung("Java_Test_Kenntnis")
                .build();

        User user = User.builder()
                .userid("testuserKenntnis")
                .password("test")
                .email("test@kenntnis.de")
                .build();

        Student student = Student.builder()
                .matrikelNummer("1234567")
                .vorname("Max")
                .nachname("Mustermann")
                .user(user)
                .build();

        student.addKenntnis(kenntnis);
        studentRepository.save(student);

        assertTrue(studentRepository.existsById(student.getId()));
        assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));

        Optional<Student> awaitStudent = studentRepository.findById(student.getId());
        assertTrue(awaitStudent.isPresent());
        Student studentFromDB = awaitStudent.get();
        assertEquals(kenntnis.getBezeichnung(), studentFromDB.getKenntnisse().get(0).getBezeichnung());

        kenntnis.removeStudent(student);
        kenntnisseRepository.save(kenntnis);

        Optional<Student> awaitStudent2 = studentRepository.findById(student.getId());
        assertTrue(awaitStudent2.isPresent());
        Student studentFromDB2 = awaitStudent.get();
        assertTrue(studentFromDB2.getKenntnisse().isEmpty());
        assertTrue(kenntnis.getStudents().isEmpty());

        studentRepository.deleteById(student.getId());
        assertFalse(studentRepository.existsById(student.getId()));
        assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));

        kenntnisseRepository.deleteById(kenntnis.getBezeichnung());

    }

}
