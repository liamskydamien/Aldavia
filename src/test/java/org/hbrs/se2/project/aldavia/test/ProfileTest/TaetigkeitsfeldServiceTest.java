package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.service.TaetigkeitsfeldService;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
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
public class TaetigkeitsfeldServiceTest {

    public static final String MESSAGE = "Wrong Exception thrown";
    public static final String MESSAGE2 = "Wrong Exception-Message thrown";

    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private TaetigkeitsfeldService taetigkeitsfeldService;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private TaetigkeitsfeldDTO taetigkeitsfeldDTO;

    private Taetigkeitsfeld taetigkeitsfeldTest;

    private final Logger logger = LoggerFactory.getLogger(TaetigkeitsfeldServiceTest.class);

    @BeforeEach
    public void setUp() {

        User user = User.builder()
                .userid("Ke21252312341234112343121432412343232414sUserK")
                .password("TestPas1241231412112432141321214313244312132443123423414223512351swor´321d")
                .email("Test@LK1342314212123412411235235523531324231441234125123512354321MisTe123st.de")
                .build();

        student = Student.builder()
                .vorname("Test_L_VornaÖ413435231412124me")
                .nachname("Test_L_Nac21512412321321412412412142421435:23124")
                .matrikelNummer("Kenntn3512312351253215413241241234321sTes124tMatrNrL")
                .build();

        student.setUser(user);

        student = studentRepository.save(student);

        taetigkeitsfeldDTO = TaetigkeitsfeldDTO.builder()
                .name("TestTaetigkei1241231235123541324tsfeld")
                .build();
    }

    @AfterEach
    public void tearDown() {
        try {
            taetigkeitsfeldTest = taetigkeitsfeldService.removeStudentFromTaetigkeitsfeld(taetigkeitsfeldDTO, student);
            taetigkeitsfeldRepository.save(taetigkeitsfeldTest);
            taetigkeitsfeldRepository.deleteById(taetigkeitsfeldTest.getBezeichnung());
        }
        catch (Exception e) {
            logger.error("Kenntnis not found");
        }
        studentRepository.deleteById(student.getId());
        taetigkeitsfeldTest = null;
    }

    @Test
    public void testAddStudentToTaetigkeitsfeldWhenTaetigkeitsfeldIsPresent() {
        taetigkeitsfeldTest = new Taetigkeitsfeld();
        taetigkeitsfeldTest.setBezeichnung(taetigkeitsfeldDTO.getName());
        taetigkeitsfeldTest = taetigkeitsfeldRepository.save(taetigkeitsfeldTest);

        taetigkeitsfeldService.addStudentToTaetigkeitsfeld(taetigkeitsfeldDTO, student);

        Taetigkeitsfeld updatedTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName()).orElseThrow();
        student = studentRepository.findById(student.getId()).orElseThrow();
        assertEquals(updatedTaetigkeitsfeld.getStudents().get(0).getId(), student.getId());
    }

    @Test
    public void testAddStudentToTaetigkeitsfeldWhenTaetigkeitsfeldIsNotPresent() {
        taetigkeitsfeldService.addStudentToTaetigkeitsfeld(taetigkeitsfeldDTO, student);
        taetigkeitsfeldTest = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName()).orElseThrow();
        student = studentRepository.findById(student.getId()).orElseThrow();
        assertEquals(taetigkeitsfeldTest.getStudents().get(0).getId(), student.getId());
    }

    @Test
    public void testRemoveStudentFromTaetigekteitsfeldWhenKenntnisIsPresent() throws org.hbrs.se2.project.aldavia.control.exception.PersistenceException {
        Taetigkeitsfeld existingTaetigkeitsfeld = Taetigkeitsfeld.builder().bezeichnung(taetigkeitsfeldDTO.getName()).build();
        taetigkeitsfeldTest = existingTaetigkeitsfeld.addStudent(student);
        taetigkeitsfeldRepository.save(taetigkeitsfeldTest);

        taetigkeitsfeldService.removeStudentFromTaetigkeitsfeld(taetigkeitsfeldDTO, student);

        Taetigkeitsfeld updatedTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName()).orElse(null);
        assertTrue(updatedTaetigkeitsfeld == null || !updatedTaetigkeitsfeld.getStudents().contains(student));
    }

    @Test
    public void testRemoveStudentFromTaetigkeitsfeldWhenTaetigkeitsfeldIsNotPresent() {
        PersistenceException taetigkeitsfeldNotFound = assertThrows(PersistenceException.class, () -> taetigkeitsfeldService.removeStudentFromTaetigkeitsfeld(taetigkeitsfeldDTO, student));

        assertEquals(taetigkeitsfeldNotFound.getPersistenceExceptionType(), PersistenceException.PersistenceExceptionType.TAETIGKEITSFELD_NOT_FOUND, MESSAGE);
        assertEquals("Taetigkeitsfeld not found", taetigkeitsfeldNotFound.getReason(), MESSAGE2);
    }

    @Test
    public void getTaetigekteitsfeldWithDTO(){
        taetigkeitsfeldTest = Taetigkeitsfeld.builder().bezeichnung(taetigkeitsfeldDTO.getName()).build();
        taetigkeitsfeldRepository.save(taetigkeitsfeldTest);
        Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldService.getTaetigkeitsfeld(taetigkeitsfeldDTO);
        assertEquals(taetigkeitsfeld.getBezeichnung(), taetigkeitsfeldDTO.getName());
    }
}