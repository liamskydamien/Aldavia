package org.hbrs.se2.project.aldavia.test.ProfileTest;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private QualifikationenService qualifikationenService;
    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository, qualifikationenService);
    }
    @Test
    public void testGetStudent() throws ProfileException {
        String userId = "testGet";
        Student student = new Student();
        Optional<Student> studentOpt = Optional.of(student);

        // Test Exception
        assertThrows(ProfileException.class, () -> studentService.getStudent(userId));

        given(studentRepository.findByUserID(userId)).willReturn(studentOpt);

        //when
        Student await = studentService.getStudent(userId);

        //then
        assertEquals(student, await);
        verify(studentRepository,times(2)).findByUserID(userId);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void testUpdateStudentInformation() throws ProfileException {
        //given
        User user = new User();
        Student student = new Student();
        student.setUser(user);
        StudentProfileDTO dto = StudentProfileDTO.builder()
                .vorname("John")
                .nachname("Doe")
                .geburtsdatum(LocalDate.now())
                .studiengang("Wirtschaftsinformatik")
                .studienbeginn(LocalDate.now())
                .matrikelNummer("12345")
                .lebenslauf("/dir/lebenslauf/lebenslauf.pdf")
                .beschreibung("Hello")
                .telefonnummer("987654321")
                .profilbild("/dir/profilbilder/1.jpg")
                .email("123@abc")
                .build();

        StudentProfileDTO dtoNull = new StudentProfileDTO();

        //when
        studentService.updateStudentInformation(student,dtoNull);
        studentService.updateStudentInformation(student,dto);
        verify(studentRepository,times(2)).save(student);
        verifyNoMoreInteractions(studentRepository);

        //then -> fällt weg, da void Methode

    }
    @Test
    public void testDeleteStudent() throws ProfileException {
        //given
        Qualifikation qualifikation = Qualifikation.builder()
                .bezeichnung("QTester")
                .beschreibung("Ability to write JUnit tests.")
                .bereich("Software Engineering 1")
                .institution("H-BRS")
                .beschaftigungsverhaltnis("Teilzeit")
                .von(LocalDate.of(2023,4, 1))
                .bis(LocalDate.of(2023, 6, 30))
                .build();

        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("IT-Dienstleister")
                .build();

        Kenntnis kenntnis = Kenntnis.builder()
                .bezeichnung("Java_Test_Kenntnis")
                .build();

        Sprache sprache = Sprache.builder()
                .bezeichnung("Testionisch")
                .level("B2")
                .build();

        User user = User.builder()
                .userid("username")
                .password("password")
                .email("test123@gmail.com")
                .build();
        Student student = Student.builder()
                .nachname("Nachname")
                .vorname("Vorname")
                .matrikelNummer("123456")
                .studiengang("Informatik")
                .studienbeginn(LocalDate.now())
                .geburtsdatum(LocalDate.of(1995, 12, 30))
                .lebenslauf("Lebenslauf")
                .user(user)
                .build();

        student.addQualifikation(qualifikation);
        student.addTaetigkeitsfeld(taetigkeitsfeld);
        student.addKenntnis(kenntnis);
        student.addSprache(sprache);

        User userNull = new User();
        Student studentNull = new Student();
        studentNull.setUser(userNull);
        //when
        studentService.deleteStudent(student);
        studentService.deleteStudent(studentNull);
        verify(studentRepository,times(1)).save(student);
        verify(studentRepository,times(1)).delete(student);
        verify(studentRepository,times(1)).save(studentNull);
        verify(studentRepository,times(1)).delete(studentNull);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void testCreateOrUpdateStudent() throws ProfileException {
        User user = new User();
        Student student = new Student();
        student.setUser(user);

        studentService.createOrUpdateStudent(student);
        verify(studentRepository,times(1)).save(student);
        verifyNoMoreInteractions(studentRepository);
    }
}
