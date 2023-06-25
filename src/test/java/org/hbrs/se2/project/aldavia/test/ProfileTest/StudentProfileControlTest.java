package org.hbrs.se2.project.aldavia.test.ProfileTest;
import org.hbrs.se2.project.aldavia.control.*;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StudentProfileControlTest {
    @Mock
    private StudentService studentService;

    @Mock
    private KenntnisseService kenntnisseService;

    @Mock
    private QualifikationenService qualifikationenService;

    @Mock
    private SprachenService sprachenService;

    @Mock
    private TaetigkeitsfeldService taetigkeitsfeldService;

    @Mock
    private StudentProfileDTOFactory studentProfileDTOFactory;

    @Mock
    private StudentProfileControl studentProfileControl;

    @BeforeEach
    public void setUp() {
        studentProfileControl = new StudentProfileControl(studentService, kenntnisseService, qualifikationenService, sprachenService, taetigkeitsfeldService, studentProfileDTOFactory);
    }

    @Test
    public void testGetStudentProfileDTO() throws ProfileException {
        //Create mock objects
        Student student = new Student();
        student.setVorname("John");
        student.setNachname("Doe");
        String userId = "test1";

        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();

        given(studentService.getStudent(userId)).willReturn(student);
        given(studentProfileDTOFactory.createStudentProfileDTO(student)).willReturn(studentProfileDTO);

        //when
        StudentProfileDTO await = studentProfileControl.getStudentProfile(userId);

        //Test Exception
        assertThrows(ProfileException.class, () -> studentProfileControl.getStudentProfile(anyString()));

        //then
        assertThat(await).hasSameClassAs(studentProfileDTO);
    }

    @Test
    public void testUpdateStudentProfile() throws ProfileException, PersistenceException {
        //Setup
        User user = new User();
        Student student = new Student();
        student.setUser(user);
        String userId = "testUpdate";
        // Create a mock StudentProfileDTO for the updated version
        StudentProfileDTO updatedVersion = StudentProfileDTO.builder()
                .vorname("John")
                .nachname("Doe")
                .geburtsdatum(LocalDate.now())
                .studiengang("Wirtschaftsinformatik")
                .studienbeginn(LocalDate.now())
                .matrikelNummer("12345")
                .lebenslauf("/dir/lebenslauf/lebenslauf.pdf")
                .beschreibung("Hello, I am John now.")
                .telefonnummer("987654321")
                .profilbild("/dir/profilbilder/1.jpg")
                .email("123@abc.de")
                .build();
        // Create a mock StudentProfileDTO for the old version
        StudentProfileDTO oldVersion = StudentProfileDTO.builder()
                .vorname("Jane")
                .nachname("Doe")
                .geburtsdatum(LocalDate.now())
                .studiengang("Wirtschaftsinformatik")
                .studienbeginn(LocalDate.now())
                .matrikelNummer("123456")
                .lebenslauf("/dir/lebenslauf/lebenslauf1.pdf")
                .beschreibung("Hello.")
                .telefonnummer("9876543210")
                .profilbild("/dir/profilbilder/2.jpg")
                .email("123@abc")
                .build();
        // Mock the behavior of getStudentProfile(username) method
        given(studentService.getStudent(userId)).willReturn(student);
        given(studentProfileDTOFactory.createStudentProfileDTO(student)).willReturn(oldVersion);
        given(studentProfileControl.getStudentProfile(userId)).willReturn(oldVersion);
        // Perform the updateStudentProfile operation
        studentProfileControl.updateStudentProfile(updatedVersion, userId);

        verify(studentService, times(1)).createOrUpdateStudent(student);
        verify(studentService, times(3)).getStudent(userId);
        verifyNoMoreInteractions(studentService);
    }
}
