package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.dao.SpracheDAO;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.SpracheDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TesteSpracheDAO {

    @Autowired
    private SpracheDAO spracheDAO;

    @Autowired
    private SprachenRepository sprachenRepository;

    @Autowired
    private TestStudentFactory testStudentFactory;

    private int id;
    private Sprache sprache;
    @BeforeEach
    public void setup() {
        sprache = new Sprache();
        sprache.setName("Deutsch_Test");
        sprache.setLevel("Muttersprache");
        sprachenRepository.save(sprache);
        id = sprache.getSpracheId();
    }

    @AfterEach
    public void teardown() {
        try{
            sprachenRepository.deleteById(id);
        }
        catch (Exception e) {
            System.out.println("Nothing to clean up");
        }
    }

    @Test
    public void testeCreateSprache(){
        try{
            SpracheDTOImpl spracheDTO = new SpracheDTOImpl();
            spracheDTO.setBezeichnung("Englisch_Test");
            spracheDTO.setLevel("Muttersprache");
            Sprache englisch = spracheDAO.createSprache(spracheDTO);
            assertTrue(sprachenRepository.findByNameAndLevel("Englisch_Test", "Muttersprache").isPresent());
            Sprache englisch2 = spracheDAO.createSprache(spracheDTO);
            assertEquals(englisch.getSpracheId(), englisch2.getSpracheId());
            sprachenRepository.deleteById(englisch.getSpracheId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testeUpdateSprache(){
        try{
            SpracheDTOImpl spracheDTO = new SpracheDTOImpl();
            spracheDTO.setBezeichnung("Englisch_Test");
            spracheDTO.setLevel("Muttersprache");
            Sprache englisch = spracheDAO.updateSprache(spracheDTO, id);
            assertTrue(sprachenRepository.findByNameAndLevel("Englisch_Test", "Muttersprache").isPresent());
            assertEquals(englisch.getSpracheId(), id);
            assertEquals(englisch.getName(), "Englisch_Test");
            assertEquals(englisch.getLevel(), "Muttersprache");
            Optional<Sprache> sprache = sprachenRepository.findById(id);
            assertTrue(sprache.isPresent());
            Sprache englisch2 = sprache.get();
            assertEquals(englisch2.getSpracheId(), id);
            assertEquals(englisch2.getName(), "Englisch_Test");
            assertEquals(englisch2.getLevel(), "Muttersprache");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testeDelete(){
        try{
            assertTrue(spracheDAO.deleteSprache(sprache));
            assertFalse(sprachenRepository.existsByNameAndLevel("Deutsch_Test", "Muttersprache"));
        }
        catch (Exception e){
            System.out.println("Fehler beim Löschen"+ e.getMessage());
        }
    }

    @Test
    public void testeAddAndDeleteStudent(){
            Student student = testStudentFactory.createStudent();
            try {
                Optional<Sprache> sprache = sprachenRepository.findById(id);
                assertTrue(sprache.isPresent());
                Sprache englisch = sprache.get();
                spracheDAO.addStudentToSprache(student, id);
                assertTrue(student.getSprachen().contains(englisch));
                spracheDAO.removeStudentFromSprache(student, id);
                assertFalse(student.getSprachen().contains(englisch));
            } catch (Exception e) {
                e.printStackTrace();
            }

            testStudentFactory.deleteStudent();
    }

}
