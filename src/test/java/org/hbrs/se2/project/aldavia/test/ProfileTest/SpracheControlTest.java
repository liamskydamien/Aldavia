package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.service.SprachenService;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SpracheControlTest {

    public static final String MESSAGE = "Wrong Exception thrown";
    public static final String MESSAGE2 = "Insufficient Exception Message";

    @Autowired
    private SprachenRepository sprachenRepository;

    @Autowired
    private SprachenService sprachenService;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private SpracheDTO spracheDTO;

    private Sprache spracheTest;

    private Logger logger = LoggerFactory.getLogger(SpracheControlTest.class);

    @BeforeEach
    public void setUp() {

        User user = User.builder()
                .userid("Sprache2134n121244124214124134134123Use13241rtest")
                .password("TestPas124123112434121123414223512351swor´321d")
                .email("Spracheuser@12431112342412421432412341232421124123434Test.de")
                .build();

        student = Student.builder()
                .vorname("Test_L_VornaÖ4112343435231412124me")
                .nachname("Test_L_Nac215124121234321321412412412142421435:23124")
                .matrikelNummer("3411241412442134124213342123112412342212434")
                .build();

        student.setUser(user);

        student = studentRepository.save(student);

        spracheDTO = SpracheDTO.builder()
                .name("TestSprache")
                .level("A1")
                .build();
    }

    @AfterEach
    public void tearDown() {
        try {
            spracheDTO.setId(spracheTest.getId());
            spracheTest = sprachenService.removeStudentFromSprache(spracheDTO, student);
            sprachenRepository.save(spracheTest);
            sprachenRepository.deleteById(spracheTest.getId());
        }
        catch (Exception e) {
            logger.error("Kenntnis not found");
        }
        studentRepository.deleteById(student.getId());
        spracheTest = null;
    }

    @Test
    public void testAddStudentToSprache_whenSpracheIsPresent() {
        spracheTest = Sprache.builder().bezeichnung(spracheDTO.getName()).level(spracheDTO.getLevel()).build();
        spracheTest = sprachenRepository.save(spracheTest);

        spracheDTO.setId(spracheTest.getId());

        sprachenService.addStudentToSprache(spracheDTO, student);

        Sprache updatedSprache = sprachenRepository.findById(spracheTest.getId()).get();
        student = studentRepository.findById(student.getId()).get();
        assertEquals(updatedSprache.getStudents().get(0).getId(), student.getId());
    }

    @Test
    public void testAddStudentToSprache_whenSpracheIsNotPresent() {
        spracheTest = sprachenService.addStudentToSprache(spracheDTO, student);
        spracheTest = sprachenRepository.findById(spracheTest.getId()).get();
        student = studentRepository.findById(student.getId()).get();
        assertEquals(spracheTest.getStudents().get(0).getId(), student.getId());
    }

    @Test
    public void testRemoveStudentFromKenntnis_whenSpracheIsPresent() throws PersistenceException {
        Sprache existingSprache = Sprache.builder().bezeichnung(spracheDTO.getName()).level(spracheDTO.getLevel()).build();
        spracheTest = existingSprache.addStudent(student);

        sprachenRepository.save(spracheTest);

        spracheDTO.setId(spracheTest.getId());

        sprachenService.removeStudentFromSprache(spracheDTO, student);

        Sprache updatedSprache = sprachenRepository.findById(spracheTest.getId()).orElseThrow();
        assertFalse(updatedSprache.getStudents().contains(student));
    }

    @Test
    public void testRemoveStudentFromSprache_whenSpracheIsNotPresent() {
        PersistenceException spracheNotFound = assertThrows(PersistenceException.class, () -> {
            sprachenService.removeStudentFromSprache(spracheDTO, student);
        });
        assertEquals(spracheNotFound.getPersistenceExceptionType(), PersistenceException.PersistenceExceptionType.SPRACHE_NOT_FOUND, MESSAGE);
        assertEquals("Sprache not found", spracheNotFound.getReason(), MESSAGE2);
    }

    @Test
    public void getSpracheWithDTO(){
        Sprache sprache = Sprache.builder().bezeichnung(spracheDTO.getName()).level(spracheDTO.getLevel()).build();
        spracheTest = sprachenRepository.save(sprache);
        spracheDTO.setId(spracheTest.getId());
        Sprache sprache1 = sprachenService.getSprache(spracheDTO);
        assertEquals(sprache1.getId(), spracheTest.getId());

        // Delete
        sprachenRepository.deleteById(spracheTest.getId());
    }
}