package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.*;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.service.KenntnisseService;
import org.hbrs.se2.project.aldavia.service.SprachenService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.hbrs.se2.project.aldavia.service.TaetigkeitsfeldService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class StudentProfileControlTest {
    public static final String WIRTSCHAFTSINFORMATIK = "Wirtschaftsinformatik";
    public static final String BESCHREIBUNG = "Ich bin ein Student.";
    public static final String PHONE = "0123456789";
    public static final String JAVA = "Java";
    public static final String LEBENSLAUF = "Ich bin ein Student.";
    public static final String SOFTWARE_ENTWICKLUNG = "Software Entwicklung";
    public static final String WIRTSCHAFTSINFORMATIK1 = "Wirtschaftsinformatik";
    public static final String PRAKTIKUM = "Praktikum";
    public static final String SOFTWARE_ENTWICKLUNG1 = "Software Entwicklung";
    public static final String BEZEICHNUNG = "SaaS Entwickler";
    public static final String SCHMIDT = "Schmidt";
    public static final String SINA = "Sina";
    public static final String USERID = "MaxMüller2001";
    public static final String PASSWORD = "TestPassword";
    public static final String MAX_MÜLLER_2001_ALDAVIA_DE = "MaxMüller2001@aldavia.de";
    public static final String MATRIKEL_NUMMER = "12345678";
    public static final String TELEFONNUMMER = "0124123456789";
    public static final String TOLLER_STUDENT = "Ich bin ein toller Student.";
    public static final String LEBENSLAUF1 = "Ich bin der größe Sascha Alda Fan";
    public static final String MATRIKELNUMMER = "9012305678";
    public static final String SASCHA_ALDA_FAN = "SaschaAldaFan";
    public static final String SOFTWARE_DESIGN = "Software Design";
    public static final String NO_CODE_GMBH = "No Code GmbH";
    public static final String SINA_SCHMIDT_ALDAVIA_MAIL_DE = "sina.schmidt@aldavia-mail.de";
    @Autowired
    private StudentProfileControl studentProfileControl;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private KenntnisseService kenntnisseService;

    @Autowired
    private SprachenService sprachenService;

    @Autowired
    private TaetigkeitsfeldService taetigkeitsfeldService;

    private Student student;
    private ChangeStudentInformationDTO changeStudentInformationDTO;

    private AddStudentInformationDTO addStudentInformationDTO;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .userid(USERID)
                .password(PASSWORD)
                .email(MAX_MÜLLER_2001_ALDAVIA_DE)
                .beschreibung(BESCHREIBUNG)
                .phone(PHONE)
                .build();

        student = Student.builder()
                .vorname("Max")
                .nachname("Müller")
                .geburtsdatum(LocalDate.of(2001, 1, 1))
                .studiengang("Informatik")
                .studienbeginn(LocalDate.of(2020, 1, 1))
                .matrikelNummer(MATRIKEL_NUMMER)
                .lebenslauf(LEBENSLAUF)
                .build();

        student.setUser(user);

        KenntnisDTO kenntnisDTO = KenntnisDTO.builder()
                .name(JAVA)
                .build();

        TaetigkeitsfeldDTO taetigkeitsfeldDTO = TaetigkeitsfeldDTO.builder()
                .name(SOFTWARE_ENTWICKLUNG)
                .build();

        SpracheDTO spracheDTO = SpracheDTO.builder()
                .name("Englisch")
                .level("C1")
                .build();

        Qualifikation qualifikation = Qualifikation.builder()
                .beschreibung("Ich habe ein Praktikum bei Aldavia absolviert.")
                .bereich(SOFTWARE_ENTWICKLUNG1)
                .bezeichnung(BEZEICHNUNG)
                .institution("Aldavia GmbH")
                .von(LocalDate.of(2020, 1, 1))
                .bis(LocalDate.of(2020, 7, 1))
                .beschaftigungsverhaltnis(PRAKTIKUM)
                .build();

        Kenntnis kenntnis = kenntnisseService.getKenntnis(kenntnisDTO);
        Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldService.getTaetigkeitsfeld(taetigkeitsfeldDTO);
        Sprache sprache = sprachenService.getSprache(spracheDTO);

        student.addKenntnis(kenntnis);
        student.addTaetigkeitsfeld(taetigkeitsfeld);
        student.addQualifikation(qualifikation);
        student.addSprache(sprache);

        student = studentRepository.save(student);

        changeStudentInformationDTO = new ChangeStudentInformationDTO();
        addStudentInformationDTO = new AddStudentInformationDTO();
    }

    @AfterEach
    void tearDown() {
        try {
            studentService.deleteStudent(student);
        } catch (Exception ignored) {

        }
    }

    @Test
    public void testGetStudent() throws ProfileException {
        StudentProfileDTO studentProfileDTO = studentProfileControl.getStudentProfile(student.getUser().getUserid());

        // Assert Student Information
        assertEquals(studentProfileDTO.getVorname(), student.getVorname());
        assertEquals(studentProfileDTO.getNachname(), student.getNachname());
        assertEquals(studentProfileDTO.getGeburtsdatum(), student.getGeburtsdatum());
        assertEquals(studentProfileDTO.getStudiengang(), student.getStudiengang());
        assertEquals(studentProfileDTO.getStudienbeginn(), student.getStudienbeginn());
        assertEquals(studentProfileDTO.getMatrikelNummer(), student.getMatrikelNummer());
        assertEquals(studentProfileDTO.getLebenslauf(), student.getLebenslauf());


        // Assert User Information
        assertEquals(studentProfileDTO.getBeschreibung(), student.getUser().getBeschreibung());
        assertEquals(studentProfileDTO.getKenntnisse().get(0).getName(), student.getKenntnisse().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getTelefonnummer(), student.getUser().getPhone());

        // Assert Qualifikation Information
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschreibung(), student.getQualifikationen().get(0).getBeschreibung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBereich(), student.getQualifikationen().get(0).getBereich());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBezeichnung(), student.getQualifikationen().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getInstitution(), student.getQualifikationen().get(0).getInstitution());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getVon(), student.getQualifikationen().get(0).getVon());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBis(), student.getQualifikationen().get(0).getBis());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschaeftigungsart(), student.getQualifikationen().get(0).getBeschaftigungsverhaltnis());

        // Assert Sprache Information
        assertEquals(studentProfileDTO.getSprachen().get(0).getName(), student.getSprachen().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getSprachen().get(0).getLevel(), student.getSprachen().get(0).getLevel());

        // Assert Taetigkeitsfeld Information
        assertEquals(studentProfileDTO.getTaetigkeitsfelder().get(0).getName(), student.getTaetigkeitsfelder().get(0).getBezeichnung());

        // Assert Kenntnis Information
        assertEquals(studentProfileDTO.getKenntnisse().get(0).getName(), student.getKenntnisse().get(0).getBezeichnung());
    }

    @Test
    public void testChangeStudentInformation() throws ProfileException, PersistenceException {
        Student student = studentRepository.findByUserID(USERID).orElseThrow();
        System.out.println("Student:" + student.getUser().getUserid());

        changeStudentInformationDTO = ChangeStudentInformationDTO.builder()
                .geburtsdatum(LocalDate.of(2001, 1, 1))
                .studiengang(WIRTSCHAFTSINFORMATIK1)
                .studienbeginn(LocalDate.of(2020, 1, 1))
                .matrikelnummer(MATRIKELNUMMER)
                .lebenslauf(LEBENSLAUF1)
                .beschreibung(TOLLER_STUDENT)
                .telefonnummer(TELEFONNUMMER)
                .build();

        // To add
        KenntnisDTO kenntnisDTOAdd = KenntnisDTO.builder()
                .name("C++")
                .build();

        SpracheDTO spracheDTOAdd = SpracheDTO.builder()
                .name("Französisch")
                .level("B2")
                .build();

        QualifikationsDTO qualifikationDTOAdd = QualifikationsDTO.builder()
                .beschreibung("Ich habe ein Praktikum bei No Code absolviert. Es hat mir nicht gefallen Adalvia war defintiv besser. Spaß :)")
                .bereich(SOFTWARE_ENTWICKLUNG)
                .bezeichnung(BEZEICHNUNG)
                .institution(NO_CODE_GMBH)
                .von(LocalDate.of(2020, 8, 1))
                .bis(LocalDate.of(2020, 8, 5))
                .beschaeftigungsart(PRAKTIKUM)
                .id(-1)
                .build();

        TaetigkeitsfeldDTO taetigkeitsfeldDTOAdd = TaetigkeitsfeldDTO.builder()
                .name(SOFTWARE_DESIGN)
                .build();

        // Create Lists
        List<TaetigkeitsfeldDTO> addTaetigkeitsfelder = new ArrayList<>();
        List<SpracheDTO> addSprachen = new ArrayList<>();
        List<QualifikationsDTO> addQulifikationen = new ArrayList<>();
        List<KenntnisDTO> addKenntnisse = new ArrayList<>();


        // Add to Lists
        addTaetigkeitsfelder.add(taetigkeitsfeldDTOAdd);
        addSprachen.add(spracheDTOAdd);
        addQulifikationen.add(qualifikationDTOAdd);
        addKenntnisse.add(kenntnisDTOAdd);


        // Build AddStudentInformationDTO
        addStudentInformationDTO.setKenntnisse(addKenntnisse);
        addStudentInformationDTO.setQualifikationen(addQulifikationen);
        addStudentInformationDTO.setSprachen(addSprachen);
        addStudentInformationDTO.setTaetigkeitsfelder(addTaetigkeitsfelder);


        StudentProfileDTO newstudentProfileDTO = StudentProfileDTO.builder()
                .email(SINA_SCHMIDT_ALDAVIA_MAIL_DE)
                .vorname(SINA)
                .nachname(SCHMIDT)
                .geburtsdatum(LocalDate.of(2001, 1, 1))
                .studiengang(WIRTSCHAFTSINFORMATIK)
                .studienbeginn(LocalDate.of(2020, 1, 1))
                .matrikelNummer(MATRIKELNUMMER)
                .lebenslauf(LEBENSLAUF1)
                .beschreibung(TOLLER_STUDENT)
                .telefonnummer(TELEFONNUMMER)
                .kenntnisse(addKenntnisse)
                .sprachen(addSprachen)
                .qualifikationen(addQulifikationen)
                .taetigkeitsfelder(addTaetigkeitsfelder)
                .build();


        studentProfileControl.updateStudentProfile(newstudentProfileDTO, student.getUser().getUserid());


        StudentProfileDTO studentProfileDTO = studentProfileControl.getStudentProfile(student.getUser().getUserid());

        // Assert Student Information
        assertEquals(studentProfileDTO.getVorname(), student.getVorname());
        assertEquals(studentProfileDTO.getNachname(), student.getNachname());
        assertEquals(studentProfileDTO.getGeburtsdatum(), changeStudentInformationDTO.getGeburtsdatum());
        assertEquals(studentProfileDTO.getStudiengang(), changeStudentInformationDTO.getStudiengang());
        assertEquals(studentProfileDTO.getStudienbeginn(), changeStudentInformationDTO.getStudienbeginn(), "Studienbeginn ist nicht gleich");
        assertEquals(studentProfileDTO.getMatrikelNummer(), changeStudentInformationDTO.getMatrikelnummer());
        assertEquals(studentProfileDTO.getLebenslauf(), changeStudentInformationDTO.getLebenslauf());

        // Assert User Information
        assertEquals(studentProfileDTO.getBeschreibung(), changeStudentInformationDTO.getBeschreibung());
        assertEquals(studentProfileDTO.getTelefonnummer(), changeStudentInformationDTO.getTelefonnummer());

        // Assert Qualifikation Information
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschreibung(), addStudentInformationDTO.getQualifikationen().get(0).getBeschreibung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBereich(), addStudentInformationDTO.getQualifikationen().get(0).getBereich());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBezeichnung(), addStudentInformationDTO.getQualifikationen().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getInstitution(), addStudentInformationDTO.getQualifikationen().get(0).getInstitution());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getVon(), addStudentInformationDTO.getQualifikationen().get(0).getVon());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBis(), addStudentInformationDTO.getQualifikationen().get(0).getBis());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschaeftigungsart(), addStudentInformationDTO.getQualifikationen().get(0).getBeschaeftigungsart());

        // Assert Sprache Information
        assertEquals(studentProfileDTO.getSprachen().get(0).getName(), addStudentInformationDTO.getSprachen().get(0).getName());
        assertEquals(studentProfileDTO.getSprachen().get(0).getLevel(), addStudentInformationDTO.getSprachen().get(0).getLevel());

        // Assert Taetigkeitsfeld Information
        assertEquals(studentProfileDTO.getTaetigkeitsfelder().get(0).getName(), addStudentInformationDTO.getTaetigkeitsfelder().get(0).getName());

        // Assert Kenntnis Information
        assertEquals(studentProfileDTO.getKenntnisse().get(0).getName(), addStudentInformationDTO.getKenntnisse().get(0).getName());

    }

    @Test
    public void addStudentInformation() throws ProfileException, PersistenceException {

        User user = User.builder()
                .userid(SASCHA_ALDA_FAN)
                .password("123456")
                .email("SaschaAldaFanNr1@AldaFans.de")
                .build();

        Student student1 = Student.builder()
                .vorname(SINA)
                .nachname(SCHMIDT)
                .build();

        student1.setUser(user);

        studentRepository.save(student1);

        Student student = studentRepository.findByUserID(SASCHA_ALDA_FAN).orElseThrow();
        System.out.println("Student:" + student.getUser().getUserid());

        changeStudentInformationDTO = ChangeStudentInformationDTO.builder()
                .geburtsdatum(LocalDate.of(2001, 1, 1))
                .studiengang(WIRTSCHAFTSINFORMATIK1)
                .studienbeginn(LocalDate.of(2020, 1, 1))
                .matrikelnummer(MATRIKELNUMMER)
                .lebenslauf(LEBENSLAUF1)
                .beschreibung(TOLLER_STUDENT)
                .telefonnummer(TELEFONNUMMER)
                .build();

        // To add
        KenntnisDTO kenntnisDTOAdd = KenntnisDTO.builder()
                .name("C++")
                .build();

        SpracheDTO spracheDTOAdd = SpracheDTO.builder()
                .name("Französisch")
                .level("B2")
                .build();

        QualifikationsDTO qualifikationDTOAdd = QualifikationsDTO.builder()
                .beschreibung("Ich habe ein Praktikum bei No Code absolviert. Es hat mir nicht gefallen Adalvia war defintiv besser. Spaß :)")
                .bereich(SOFTWARE_ENTWICKLUNG1)
                .bezeichnung(BEZEICHNUNG)
                .institution(NO_CODE_GMBH)
                .von(LocalDate.of(2020, 8, 1))
                .bis(LocalDate.of(2020, 8, 5))
                .beschaeftigungsart(PRAKTIKUM)
                .id(-1)
                .build();

        TaetigkeitsfeldDTO taetigkeitsfeldDTOAdd = TaetigkeitsfeldDTO.builder()
                .name(SOFTWARE_DESIGN)
                .build();

        // Create Lists
        List<TaetigkeitsfeldDTO> addTaetigkeitsfelder = new ArrayList<>();
        List<SpracheDTO> addSprachen = new ArrayList<>();
        List<QualifikationsDTO> addQulifikationen = new ArrayList<>();
        List<KenntnisDTO> addKenntnisse = new ArrayList<>();


        // Add to Lists
        addTaetigkeitsfelder.add(taetigkeitsfeldDTOAdd);
        addSprachen.add(spracheDTOAdd);
        addQulifikationen.add(qualifikationDTOAdd);
        addKenntnisse.add(kenntnisDTOAdd);

        // Build AddStudentInformationDTO
        addStudentInformationDTO.setKenntnisse(addKenntnisse);
        addStudentInformationDTO.setQualifikationen(addQulifikationen);
        addStudentInformationDTO.setSprachen(addSprachen);
        addStudentInformationDTO.setTaetigkeitsfelder(addTaetigkeitsfelder);


        StudentProfileDTO newstudentProfileDTO = StudentProfileDTO.builder()
                .email(SINA_SCHMIDT_ALDAVIA_MAIL_DE)
                .vorname(SINA)
                .nachname(SCHMIDT)
                .geburtsdatum(LocalDate.of(2001, 1, 1))
                .studiengang(WIRTSCHAFTSINFORMATIK1)
                .studienbeginn(LocalDate.of(2020, 1, 1))
                .matrikelNummer(MATRIKELNUMMER)
                .lebenslauf(LEBENSLAUF1)
                .beschreibung(TOLLER_STUDENT)
                .telefonnummer(TELEFONNUMMER)
                .kenntnisse(addKenntnisse)
                .sprachen(addSprachen)
                .qualifikationen(addQulifikationen)
                .taetigkeitsfelder(addTaetigkeitsfelder)
                .build();


        studentProfileControl.updateStudentProfile(newstudentProfileDTO, student1.getUser().getUserid());


        StudentProfileDTO studentProfileDTO = studentProfileControl.getStudentProfile(student1.getUser().getUserid());

        // Assert Student Information
        assertEquals(studentProfileDTO.getVorname(), student1.getVorname());
        assertEquals(studentProfileDTO.getNachname(), student1.getNachname());
        assertEquals(studentProfileDTO.getGeburtsdatum(), changeStudentInformationDTO.getGeburtsdatum());
        assertEquals(studentProfileDTO.getStudiengang(), changeStudentInformationDTO.getStudiengang());
        assertEquals(studentProfileDTO.getStudienbeginn(), changeStudentInformationDTO.getStudienbeginn(), "Studienbeginn ist nicht gleich");
        assertEquals(studentProfileDTO.getMatrikelNummer(), changeStudentInformationDTO.getMatrikelnummer());
        assertEquals(studentProfileDTO.getLebenslauf(), changeStudentInformationDTO.getLebenslauf());

        // Assert User Information
        assertEquals(studentProfileDTO.getBeschreibung(), changeStudentInformationDTO.getBeschreibung());
        assertEquals(studentProfileDTO.getTelefonnummer(), changeStudentInformationDTO.getTelefonnummer());

        // Assert Qualifikation Information
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschreibung(), addStudentInformationDTO.getQualifikationen().get(0).getBeschreibung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBereich(), addStudentInformationDTO.getQualifikationen().get(0).getBereich());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBezeichnung(), addStudentInformationDTO.getQualifikationen().get(0).getBezeichnung());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getInstitution(), addStudentInformationDTO.getQualifikationen().get(0).getInstitution());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getVon(), addStudentInformationDTO.getQualifikationen().get(0).getVon());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBis(), addStudentInformationDTO.getQualifikationen().get(0).getBis());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschaeftigungsart(), addStudentInformationDTO.getQualifikationen().get(0).getBeschaeftigungsart());

        // Assert Sprache Information
        assertEquals(studentProfileDTO.getSprachen().get(0).getName(), addStudentInformationDTO.getSprachen().get(0).getName());
        assertEquals(studentProfileDTO.getSprachen().get(0).getLevel(), addStudentInformationDTO.getSprachen().get(0).getLevel());

        // Assert Taetigkeitsfeld Information
        assertEquals(studentProfileDTO.getTaetigkeitsfelder().get(0).getName(), addStudentInformationDTO.getTaetigkeitsfelder().get(0).getName());

        // Assert Kenntnis Information
        assertEquals(studentProfileDTO.getKenntnisse().get(0).getName(), addStudentInformationDTO.getKenntnisse().get(0).getName());


    }

}
