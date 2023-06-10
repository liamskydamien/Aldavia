package org.hbrs.se2.project.aldavia.test.DatabaseTest;


import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SpracheTest {
    //TODO: Fix this test
    //TODO: Add round trip test for Sprache
    //TODO: Test Constraints if student gets deleted (cascade) -> Sprache should not get deleted too
    //TODO: Test add... and remove... methods

    @Autowired
    private SprachenRepository sprachenRepository;

    @Autowired
    private StudentRepository studentRepository;

    User user = User.builder()
            .email("Student1@qtest.vn")
            .userid("QStudent1")
            .password("qwedfghbn")
            .build();

    Student student = Student.builder()
            .nachname("Nguyen")
            .vorname("Qtest")
            .user(user)
            .matrikelNummer("963852")
            .build();

    Sprache sprache = Sprache.builder()
            .bezeichnung("Testionisch")
            .level("B2")
            .build();

    @Test
    public void roundTripTest(){

        // Create / Setup
        sprachenRepository.save(sprache);
        //Saved in DB?
        assertTrue(sprachenRepository.existsById(sprache.getId()));

        // Read
        Optional<Sprache> awaitSprache = sprachenRepository.findById(sprache.getId());
        assertTrue(awaitSprache.isPresent());
        assertEquals(awaitSprache.get().getId(), sprache.getId());
        assertEquals(awaitSprache.get().getLevel(), sprache.getLevel());
        assertEquals(awaitSprache.get().getBezeichnung(), sprache.getBezeichnung());

        // Update
        sprache.setLevel("C1");
        sprachenRepository.save(sprache);
        awaitSprache = sprachenRepository.findById(sprache.getId());
        assertTrue(awaitSprache.isPresent());
        assertEquals(awaitSprache.get().getId(), sprache.getId());
        assertEquals(awaitSprache.get().getLevel(), sprache.getLevel());
        assertEquals(awaitSprache.get().getBezeichnung(), sprache.getBezeichnung());

        // Delete
        sprachenRepository.deleteById(sprache.getId());
        assertFalse(sprachenRepository.existsById(sprache.getId()));
    }

    @Test
    public void negativeTests(){
        assertThrows(Exception.class, () -> sprachenRepository.save(null));
        assertThrows(Exception.class, () -> sprachenRepository.deleteById(-1));
    }

    @Test
    public void studentTest() {
        // Setup
        studentRepository.save(student);
        List<Student> students = new ArrayList<>();
        students.add(student);

        sprache.setStudents(students);
        sprachenRepository.save(sprache);
        List<Sprache> sprachen = new ArrayList<>();
        sprachen.add(sprache);


        // Update Student
        student.setSprachen(sprachen);
        studentRepository.save(student);

        // Read
        Optional<Sprache> awaitSprache = sprachenRepository.findById(sprache.getId());
        assertTrue(awaitSprache.isPresent());
        assertEquals(awaitSprache.get().getId(), sprache.getId());
        assertEquals(awaitSprache.get().getLevel(), sprache.getLevel());
        assertEquals(awaitSprache.get().getBezeichnung(), sprache.getBezeichnung());
        assertEquals(awaitSprache.get().getStudents().get(0).getId(), sprache.getStudents().get(0).getId());

        // Delete
        studentRepository.delete(student);
        assertFalse(studentRepository.existsById(student.getId()));
        //Sprachen dürfen nicht mit gelöscht sein
        assertTrue(sprachenRepository.existsById(sprache.getId()));
        sprachenRepository.deleteById(sprache.getId());
    }


}
