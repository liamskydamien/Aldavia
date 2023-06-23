package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StudentProfileDTOFactoryTest {

    private StudentProfileDTOFactory studentProfileDTOFactory = StudentProfileDTOFactory.getInstance();
    @Test
    public void testEmpty() throws ProfileException {
        Student student = Student.builder()
                .user(User.builder()
                        .userid("username")
                        .password("password")
                        .email("email")
                        .build())
                .build();
        StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student);
        assertEquals(studentProfileDTO.getVorname(), "");
        assertEquals(studentProfileDTO.getNachname(), "");
        assertEquals(studentProfileDTO.getGeburtsdatum().getDayOfYear(), LocalDate.now().getDayOfYear());
        assertEquals(studentProfileDTO.getGeburtsdatum().getMonthValue(), LocalDate.now().getMonthValue());
        assertEquals(studentProfileDTO.getGeburtsdatum().getYear(), LocalDate.now().getYear());
        assertEquals(studentProfileDTO.getTelefonnummer(), "");
        assertEquals(studentProfileDTO.getLebenslauf(), "");
        assertEquals(studentProfileDTO.getStudiengang(), "");
        assertEquals(studentProfileDTO.getStudienbeginn().getDayOfYear(), LocalDate.now().getDayOfYear());
        assertEquals(studentProfileDTO.getStudienbeginn().getMonthValue(), LocalDate.now().getMonthValue());
        assertEquals(studentProfileDTO.getStudienbeginn().getYear(), LocalDate.now().getYear());
        assertEquals(studentProfileDTO.getBeschreibung(), "");
        assertEquals(studentProfileDTO.getUsername(), "username");

    }

    @Test
    public void testFull() throws ProfileException {

        Kenntnis kenntnis = Kenntnis.builder()
                .bezeichnung("bezeichnung")
                .build();

        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("bezeichnung")
                .build();

        Sprache sprache = Sprache.builder()
                .bezeichnung("bezeichnung")
                .level("level")
                .build();

        Qualifikation qualifikation = Qualifikation.builder()
                .bezeichnung("bezeichnung")
                .bereich("bereich")
                .institution("institution")
                .von(LocalDate.of(2000, 1, 1))
                .bis(LocalDate.of(2000, 1, 1))
                .beschreibung("beschreibung")
                .beschaftigungsverhaltnis("beschaftigungsverhaltnis")
                .id(1)
                .build();

        Student student = Student.builder()
                .user(User.builder()
                        .userid("username")
                        .password("password")
                        .email("email")
                        .phone("phone")
                        .profilePicture("profilePicture")
                        .beschreibung("beschreibung")
                        .build())
                .vorname("vorname")
                .nachname("nachname")
                .geburtsdatum(LocalDate.of(2000, 1, 1))
                .studienbeginn(LocalDate.of(2000, 1, 1))
                .lebenslauf("lebenslauf")
                .studiengang("studiengang")
                .matrikelNummer("matrikelNummer")
                .taetigkeitsfelder(List.of(taetigkeitsfeld))
                .kenntnisse(List.of(kenntnis))
                .sprachen(List.of(sprache))
                .qualifikationen(List.of(qualifikation))
                .build();

        StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student);
        assertEquals(studentProfileDTO.getVorname(), "vorname");
        assertEquals(studentProfileDTO.getNachname(), "nachname");
        assertEquals(studentProfileDTO.getGeburtsdatum().getDayOfYear(), LocalDate.of(2000, 1, 1).getDayOfYear());
        assertEquals(studentProfileDTO.getGeburtsdatum().getMonthValue(), LocalDate.of(2000, 1, 1).getMonthValue());
        assertEquals(studentProfileDTO.getGeburtsdatum().getYear(), LocalDate.of(2000, 1, 1).getYear());
        assertEquals(studentProfileDTO.getTelefonnummer(), "phone");
        assertEquals(studentProfileDTO.getLebenslauf(), "lebenslauf");
        assertEquals(studentProfileDTO.getStudiengang(), "studiengang");
        assertEquals(studentProfileDTO.getStudienbeginn().getDayOfYear(), LocalDate.of(2000, 1, 1).getDayOfYear());
        assertEquals(studentProfileDTO.getStudienbeginn().getMonthValue(), LocalDate.of(2000, 1, 1).getMonthValue());
        assertEquals(studentProfileDTO.getStudienbeginn().getYear(), LocalDate.of(2000, 1, 1).getYear());
        assertEquals(studentProfileDTO.getUsername(), "username");
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBezeichnung(), "bezeichnung");
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBereich(), "bereich");
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getInstitution(), "institution");
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getVon().getDayOfYear(), LocalDate.of(2000, 1, 1).getDayOfYear());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getVon().getMonthValue(), LocalDate.of(2000, 1, 1).getMonthValue());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getVon().getYear(), LocalDate.of(2000, 1, 1).getYear());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBis().getDayOfYear(), LocalDate.of(2000, 1, 1).getDayOfYear());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBis().getMonthValue(), LocalDate.of(2000, 1, 1).getMonthValue());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBis().getYear(), LocalDate.of(2000, 1, 1).getYear());
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschreibung(), "beschreibung");
        assertEquals(studentProfileDTO.getQualifikationen().get(0).getBeschaeftigungsart(), "beschaftigungsverhaltnis");
        assertEquals(studentProfileDTO.getKenntnisse().get(0).getName(), "bezeichnung");
        assertEquals(studentProfileDTO.getTaetigkeitsfelder().get(0).getName(), "bezeichnung");
        assertEquals(studentProfileDTO.getSprachen().get(0).getName(), "bezeichnung");
        assertEquals(studentProfileDTO.getSprachen().get(0).getLevel(), "level");
        assertEquals(studentProfileDTO.getBeschreibung(), "beschreibung");
        assertEquals(studentProfileDTO.getProfilbild(), "profilePicture");
        assertEquals(studentProfileDTO.getEmail(), "email");
        assertEquals(studentProfileDTO.getMatrikelNummer(), "matrikelNummer");
    }

    @Test
    public void testNegative(){
        Student student = new Student();
        assertThrows(ProfileException.class, () -> studentProfileDTOFactory.createStudentProfileDTO(student));
    }
}
