package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.service.KenntnisseService;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
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
public class KenntnisseServiceTest {

    public static final String MESSAGE = "Wrong Exception thrown";
    public static final String MESSAGE2 = "Wrong Exception-Message thrown";

    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    @Autowired
    private KenntnisseService kenntnisseService;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private KenntnisDTO kenntnisDTO;

    private Kenntnis kenntnisTest;

    private Logger logger = LoggerFactory.getLogger(KenntnisseServiceTest.class);

    @BeforeEach
    public void setUp() {

        User user = User.builder()
                .userid("Ke21252314sUserK")
                .password("TestPas12412314121123414223512351swor´321d")
                .email("Test@LK134231421241234125123512354321MisTe123st.de")
                .build();

        student = Student.builder()
                .vorname("Test_L_VornaÖ413435231412124me")
                .nachname("Test_L_Nac21512412321321412412412142421435:23124")
                .matrikelNummer("Kenntn35sTes124tMatrNrL")
                .build();

        student.setUser(user);

        student = studentRepository.save(student);

        kenntnisDTO = new KenntnisDTO("TestK1121124312421512342421§enntnis_3");
    }

   @AfterEach
    public void tearDown() {
        try {
            kenntnisseService.removeStudentFromKenntnis(kenntnisDTO, student);
            kenntnisseRepository.deleteById(kenntnisTest.getBezeichnung());
        }
        catch (Exception e) {
            logger.error("Kenntnis not found");
        }
        studentRepository.deleteById(student.getId());
        kenntnisTest = null;
    }

    @Test
    public void testAddStudentToKenntnis_whenKenntnisIsPresent() {
        kenntnisTest = Kenntnis.builder().bezeichnung(kenntnisDTO.getName()).build();
        kenntnisseRepository.save(kenntnisTest);

        kenntnisseService.addStudentToKenntnis(kenntnisDTO, student);

        Kenntnis updatedKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName()).orElseThrow();
        student = studentRepository.findById(student.getId()).orElseThrow();
        assertEquals(updatedKenntnis.getStudents().get(0).getId(), student.getId());
    }

    @Test
    public void testAddStudentToKenntnis_whenKenntnisIsNotPresent() {
        kenntnisseService.addStudentToKenntnis(kenntnisDTO, student);
        kenntnisTest = kenntnisseRepository.findById(kenntnisDTO.getName()).orElseThrow();
        student = studentRepository.findById(student.getId()).orElseThrow();
        assertEquals(kenntnisTest.getStudents().get(0).getId(), student.getId());
    }

    @Test
    public void testRemoveStudentFromKenntnis_whenKenntnisIsPresent() throws org.hbrs.se2.project.aldavia.control.exception.PersistenceException {
        Kenntnis existingKenntnis = Kenntnis.builder().bezeichnung(kenntnisDTO.getName()).build();
        kenntnisTest = existingKenntnis.addStudent(student);
        kenntnisseRepository.save(kenntnisTest);

        kenntnisseService.removeStudentFromKenntnis(kenntnisDTO, student);

        Kenntnis updatedKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName()).orElse(null);
        assertTrue(updatedKenntnis == null || !updatedKenntnis.getStudents().contains(student));
    }

    @Test
    public void testRemoveStudentFromKenntnis_whenKenntnisIsNotPresent() {
        PersistenceException kenntnisNotPresent = assertThrows(PersistenceException.class, () -> {
            kenntnisseService.removeStudentFromKenntnis(kenntnisDTO, student);
        });

        assertEquals(kenntnisNotPresent.getPersistenceExceptionType(), PersistenceException.PersistenceExceptionType.KENNTNIS_NOT_FOUND, MESSAGE);
        assertEquals("Kenntnis not found", kenntnisNotPresent.getReason(), MESSAGE2);
    }

    @Test
    public void testGetKenntnisWithDTO(){
        Kenntnis kenntnis = Kenntnis.builder().bezeichnung(kenntnisDTO.getName()).build();
        kenntnisseRepository.save(kenntnis);

        Kenntnis kenntnisDTO = kenntnisseService.getKenntnis(this.kenntnisDTO);

        assertEquals(kenntnisDTO.getBezeichnung(), kenntnis.getBezeichnung());
    }
}