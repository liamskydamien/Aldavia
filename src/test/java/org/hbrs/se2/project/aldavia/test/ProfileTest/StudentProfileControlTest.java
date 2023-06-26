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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StudentProfileControlTest {
    public static final String RANDOM_123 = "random123";
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
        QualifikationsDTO qualifikationDTO = QualifikationsDTO.builder()
                .bezeichnung("QTester")
                .build();
        List<QualifikationsDTO> qualifikationenDTO = new ArrayList<>();
        qualifikationenDTO.add(qualifikationDTO);

        TaetigkeitsfeldDTO taetigkeitsfeldDTO = TaetigkeitsfeldDTO.builder()
                .name("IT-Dienstleister")
                .build();
        List<TaetigkeitsfeldDTO> taetigkeitsfelderDTO = new ArrayList<>();
        taetigkeitsfelderDTO.add(taetigkeitsfeldDTO);

        KenntnisDTO kenntnisDTO = KenntnisDTO.builder()
                .name("Java_Test_Kenntnis")
                .build();
        List<KenntnisDTO> kenntnisseDTO = new ArrayList<>();
        kenntnisseDTO.add(kenntnisDTO);

        SpracheDTO spracheDTO = SpracheDTO.builder()
                .name("Testionisch")
                .level("B2")
                .build();
        List<SpracheDTO> sprachenDTO = new ArrayList<>();
        sprachenDTO.add(spracheDTO);

        Qualifikation qualifikation = new Qualifikation();
        List<Qualifikation> qualifikationen = new ArrayList<>();
        qualifikationen.add(qualifikation);

        Taetigkeitsfeld taetigkeitsfeld = new Taetigkeitsfeld();
        List<Taetigkeitsfeld> taetigkeitsfelder = new ArrayList<>();
        taetigkeitsfelder.add(taetigkeitsfeld);

        Kenntnis kenntnis = new Kenntnis();
        List<Kenntnis> kenntnisse = new ArrayList<>();
        kenntnisse.add(kenntnis);

        Sprache sprache = new Sprache();
        List<Sprache> sprachen = new ArrayList<>();
        sprachen.add(sprache);

        User user = new User();
        Student student = new Student();
        student.setUser(user);
        student.setQualifikationen(qualifikationen);
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        student.setKenntnisse(kenntnisse);
        student.setSprachen(sprachen);
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
                .qualifikationen(qualifikationenDTO)
                .taetigkeitsfelder(taetigkeitsfelderDTO)
                .kenntnisse(kenntnisseDTO)
                .sprachen(sprachenDTO)
                .build();

        // Create a mock StudentProfileDTO for the old version
        StudentProfileDTO oldVersion = StudentProfileDTO.builder()
                .vorname("Jane")
                .nachname("Doe")
                .geburtsdatum(LocalDate.now())
                .studiengang("Wirtschaftsinformatik")
                .studienbeginn(LocalDate.now())
                .matrikelNummer("123456")
                .email("123@abc")
                .qualifikationen(qualifikationenDTO)
                .taetigkeitsfelder(taetigkeitsfelderDTO)
                .kenntnisse(kenntnisseDTO)
                .sprachen(sprachenDTO)
                .build();

        //Test Exception
        assertThrows(ProfileException.class, () -> studentProfileControl.updateStudentProfile(new StudentProfileDTO(), RANDOM_123));

        // Mock the behavior of getStudentProfile(username) method
        given(studentService.getStudent(userId)).willReturn(student);
        given(studentProfileDTOFactory.createStudentProfileDTO(student)).willReturn(oldVersion);
        given(studentProfileControl.getStudentProfile(userId)).willReturn(oldVersion);
        // Perform the updateStudentProfile operation
        studentProfileControl.updateStudentProfile(updatedVersion, userId);

        verify(studentService, times(1)).createOrUpdateStudent(student);
        verify(studentService, times(3)).getStudent(userId);
        verify(studentService, times(1)).getStudent(RANDOM_123);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    public void testUpdateStudentProfileNull() throws ProfileException, PersistenceException {
        //Setup
        User user = new User();
        Student student = new Student();
        student.setUser(user);
        String userId = "testUpdate";
        // Create a mock StudentProfileDTO for the updated version
        StudentProfileDTO updatedVersionNull = StudentProfileDTO.builder()
                .build();
        // Create a mock StudentProfileDTO for the old version
        StudentProfileDTO oldVersion = StudentProfileDTO.builder()
                .build();

        //Test Exception
        assertThrows(ProfileException.class, () -> studentProfileControl.updateStudentProfile(new StudentProfileDTO(), RANDOM_123));

        // Mock the behavior of getStudentProfile(username) method
        given(studentService.getStudent(userId)).willReturn(student);
        given(studentProfileDTOFactory.createStudentProfileDTO(student)).willReturn(oldVersion);
        given(studentProfileControl.getStudentProfile(userId)).willReturn(oldVersion);
        // Perform the updateStudentProfile operation

        studentProfileControl.updateStudentProfile(updatedVersionNull, userId);


        verify(studentService, times(1)).createOrUpdateStudent(student);
        verify(studentService, times(3)).getStudent(userId);
        verify(studentService, times(1)).getStudent(RANDOM_123);
        verifyNoMoreInteractions(studentService);
    }
}
