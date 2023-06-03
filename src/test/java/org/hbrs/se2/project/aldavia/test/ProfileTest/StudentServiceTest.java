package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.service.StudentService;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.ChangeStudentInformationDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    private Student student;
    private ChangeStudentInformationDTO changeStudentInformationDTO;

    private Kenntnis kenntnis;
    private Taetigkeitsfeld taetigkeitsfeld;
    private Sprache sprache;
    private Qualifikation qualifikation;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .userid("TestUser")
                .password("TestPassword")
                .email("TestEmail")
                .build();

        student = Student.builder()
                .vorname("TestVorname")
                .nachname("TestNachname")
                .build();

        student.setUser(user);

        kenntnis = Kenntnis.builder()
                .bezeichnung("TestKenntnis")
                .build();

        taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("TestTaetigkeitsfeld")
                .build();

        sprache = Sprache.builder()
                .bezeichnung("TestSprache")
                .level("TestLevel")
                .build();

        qualifikation = Qualifikation.builder()
                .beschreibung("TestBeschreibung")
                .bereich("TestBereich")
                .bezeichnung("TestBezeichnung")
                .institution("TestInstitution")
                .von(LocalDate.of(2020, 1, 1))
                .bis(LocalDate.of(2020, 1, 1))
                .beschaftigungsverhaltnis("TestBeschaeftigungsverhaeltnis")
                .build();

        student.addKenntnis(kenntnis);
        student.addTaetigkeitsfeld(taetigkeitsfeld);
        student.addQualifikation(qualifikation);
        student.addSprache(sprache);

        student = studentRepository.save(student);
    }

    @AfterEach
    void tearDown() {
        try {
            student.removeKenntnis(kenntnis);
            student.removeTaetigkeitsfeld(taetigkeitsfeld);
            student.removeQualifikation(qualifikation);
            student.removeSprache(sprache);
            studentRepository.deleteById(student.getId());
        }
        catch (Exception e) {
            try {
                studentRepository.deleteById(student.getId());
            }
            catch (Exception ex) {
                System.out.println("Student konnte nicht gelöscht werden.");
            }
        }
        kenntnis = null;
        taetigkeitsfeld = null;
        sprache = null;
        qualifikation = null;
        student = null;
        changeStudentInformationDTO = null;
    }

    @Test
    void testGetStudent_StudentExists_ReturnsStudent() throws ProfileException {
        Student newStudent = studentService.getStudent(student.getUser().getUserid());
        assertEquals(student.getVorname(), newStudent.getVorname());
        assertEquals(student.getNachname(), newStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), newStudent.getMatrikelNummer());
        assertEquals(student.getUser().getUserid(), newStudent.getUser().getUserid());
    }

    @Test
    void testGetStudent_StudentDoesNotExist_ThrowsProfileException() {
        assertThrows(ProfileException.class, () -> studentService.getStudent("TestUserDoesNotExist_xyz"));
    }

    @Test
    void testCreateOrUpdateStudent_ValidStudent_StudentSavedSuccessfully() throws ProfileException {
       changeStudentInformationDTO = ChangeStudentInformationDTO.builder()
               .beschreibung("TestBeschreibung")
               .email("TestEmail")
               .lebenslauf("TestLebenslauf")
               .profilbild("TestProfilbild")
               .telefonnummer("TestTelefonnummer")
               .vorname("TestVorname2")
               .build();

       studentService.updateStudentInformation(student, changeStudentInformationDTO);

       Student newStudent = studentService.getStudent(student.getUser().getUserid());
       assertEquals(student.getVorname(), newStudent.getVorname());
       assertEquals(student.getNachname(), newStudent.getNachname());
       assertEquals(student.getMatrikelNummer(), newStudent.getMatrikelNummer());
       assertEquals(student.getUser().getUserid(), newStudent.getUser().getUserid());
       assertEquals(student.getUser().getBeschreibung(), newStudent.getUser().getBeschreibung());
       assertEquals(student.getUser().getEmail(), newStudent.getUser().getEmail());
       assertEquals(student.getLebenslauf(), newStudent.getLebenslauf());
       assertEquals(student.getUser().getProfilePicture(), newStudent.getUser().getProfilePicture());

    }

    @Test
    void testDeleteStudent_ValidStudent_StudentDeletedSuccessfully() throws ProfileException {
        studentService.deleteStudent(student);
        assertFalse(studentRepository.existsById(student.getId()));
    }
}
