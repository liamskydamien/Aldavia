package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import com.vaadin.flow.component.html.Span;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpracheTest {
    @Autowired
    private SprachenRepository sprachenRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestStudentFactory testStudentFactory;

    int studentId, userId, sprachenId;

    @Test
    public void roundTripTest(){

        // Create
        Sprache sprache = new Sprache();
        sprache.setName("Testionisch");
        sprache.setLevel("B2");
        sprachenRepository.save(sprache);
        sprachenId = sprache.getSpracheId();

        // Read
        Optional<Sprache> awaitSprache = sprachenRepository.findById(sprachenId);
        assertTrue(awaitSprache.isPresent());
        assertEquals(awaitSprache.get().getName(), sprache.getName(), awaitSprache.get().getName() + " != " + sprache.getName());
        assertEquals(awaitSprache.get().getLevel(), sprache.getLevel(), awaitSprache.get().getLevel() + " != " + sprache.getLevel());
        assertEquals(awaitSprache.get().getSpracheId(), sprache.getSpracheId(), awaitSprache.get().getSpracheId() + " != " + sprache.getSpracheId());

        // Update
        sprache.setLevel("C1");
        sprachenRepository.save(sprache);
        awaitSprache = sprachenRepository.findById(sprachenId);
        assertTrue(awaitSprache.isPresent());
        assertEquals(awaitSprache.get().getName(), sprache.getName(), awaitSprache.get().getName() + " != " + sprache.getName());
        assertEquals(awaitSprache.get().getLevel(), sprache.getLevel(), awaitSprache.get().getLevel() + " != " + sprache.getLevel());
        assertEquals(awaitSprache.get().getSpracheId(), sprache.getSpracheId(), awaitSprache.get().getSpracheId() + " != " + sprache.getSpracheId());

        // Delete
        sprachenRepository.deleteById(sprachenId);
        assertFalse(sprachenRepository.existsById(sprachenId));
    }

    @Test
    public void negativeTests(){
        assertThrows(Exception.class, () -> {
            sprachenRepository.save(null);
        });
        assertThrows(Exception.class, () -> {
            sprachenRepository.deleteById(-1);
        });
    }

    @Test
    public void studentTest() {
        Student student = testStudentFactory.createStudent();
        studentId = student.getStudentId();

        List<Student> students = new ArrayList<>();
        students.add(student);

        // Create Sprache
        Sprache sprache = new Sprache();
        sprache.setName("Testionisch");
        sprache.setLevel("B2");
        sprache.setStudenten(students);
        sprachenRepository.save(sprache);
        sprachenId = sprache.getSpracheId();

        List<Sprache> sprachen = new ArrayList<>();
        sprachen.add(sprache);

        // Update Student
        student.setSprachen(sprachen);
        studentRepository.save(student);

        // Read
        Optional<Sprache> awaitSprache = sprachenRepository.findById(sprachenId);
        assertTrue(awaitSprache.isPresent());
        assertEquals(awaitSprache.get().getName(), sprache.getName(), awaitSprache.get().getName() + " != " + sprache.getName());
        assertEquals(awaitSprache.get().getLevel(), sprache.getLevel(), awaitSprache.get().getLevel() + " != " + sprache.getLevel());
        assertEquals(awaitSprache.get().getSpracheId(), sprache.getSpracheId(), awaitSprache.get().getSpracheId() + " != " + sprache.getSpracheId());
        assertEquals(awaitSprache.get().getStudenten().get(0).getVorname(), sprache.getStudenten().get(0).getVorname(), awaitSprache.get().getStudenten().get(0).getVorname() + " != " + sprache.getStudenten().get(0).getVorname());

        // Teardown
        student.setSprachen(null);
        studentRepository.save(student);

        sprachenRepository.deleteById(sprachenId);
        testStudentFactory.deleteStudent();
    }
}
