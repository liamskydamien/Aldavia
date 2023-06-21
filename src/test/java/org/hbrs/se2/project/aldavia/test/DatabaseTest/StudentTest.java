package org.hbrs.se2.project.aldavia.test.DatabaseTest;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTest {

    public static final String BESCHREIBUNG = "Werkstudent im Bereich Softwareentwicklung";
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolleRepository rolleRepository;

    @Autowired
    KenntnisseRepository kenntnisseRepository;

    @Autowired
    private SprachenRepository sprachenRepository;

    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    @Autowired
    private BewerbungRepository bewerbungRepository;

    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        User user = User.builder()
                .userid("username")
                .password("password")
                .email("test123@gmail.com")
                .build();
        userRepository.save(user);

        Rolle rolle = Rolle.builder()
                .bezeichnung("Student")
                .build();
        rolle.addUser(user);
        rolleRepository.save(rolle);

        user.addRolle(rolle);

        student = Student.builder()
                .nachname("Nachname")
                .vorname("Vorname")
                .matrikelNummer("123456")
                .studiengang("Informatik")
                .studienbeginn(LocalDate.now())
                .geburtsdatum(LocalDate.of(1995, 12, 30))
                .lebenslauf("Lebenslauf")
                .user(user)
                .build();

        studentRepository.save(student);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testRoundTrip() {
        Optional<Student> wrapperStudent = studentRepository.findById(student.getId());
        Student foundStudent = null;
        if(wrapperStudent.isPresent()){
            foundStudent = wrapperStudent.get();
        }
        assert foundStudent != null;
        assertEquals(student.getId(), foundStudent.getId());
        assertEquals(student.getNachname(), foundStudent.getNachname());
        assertEquals(student.getVorname(), foundStudent.getVorname());
        assertEquals(student.getMatrikelNummer(), foundStudent.getMatrikelNummer());
        assertEquals(student.getStudiengang(), foundStudent.getStudiengang());
        assertEquals(student.getStudienbeginn(), foundStudent.getStudienbeginn());
        assertEquals(student.getGeburtsdatum(), foundStudent.getGeburtsdatum());
        assertEquals(student.getLebenslauf(), foundStudent.getLebenslauf());

    }

    @Test
    public void testDeleteCascade() {
        //Kenntnisse hinzufügen
        Kenntnis kenntnis = Kenntnis.builder()
                .bezeichnung("Java")
                .build();
        kenntnis.addStudent(student);
        kenntnisseRepository.save(kenntnis);
        student.addKenntnis(kenntnis);

        //Sprachen hinzufügen
        Sprache sprache = Sprache.builder()
                .bezeichnung("Deutsch")
                .level("Muttersprache")
                .build();
        sprache.addStudent(student);
        sprachenRepository.save(sprache);
        student.addSprache(sprache);

        //Qualifikationen hinzufügen
        Qualifikation qualifikation = Qualifikation.builder()
                .beschreibung(BESCHREIBUNG)
                .build();
        qualifikation.setStudent(student);
        student.addQualifikation(qualifikation);

        //Taetigkeitsfeld hinzufügen
        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                .bezeichnung("Softwareentwicklung")
                .build();
        taetigkeitsfeld.addStudent(student);
        taetigkeitsfeldRepository.save(taetigkeitsfeld);
        student.addTaetigkeitsfeld(taetigkeitsfeld);

        studentRepository.delete(student);

        assertFalse(studentRepository.existsById(student.getId()));
        assertFalse(userRepository.existsById(student.getUser().getId()));
        assertTrue(rolleRepository.existsById(student.getUser().getRollen().get(0).getBezeichnung()));
        assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));
        assertTrue(sprachenRepository.existsById(sprache.getId()));
        assertTrue(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));
    }


    @Test
    public void testAddAndRemoveKenntnisse() {
        Kenntnis kenntnis = Kenntnis.builder()
                            .bezeichnung("Java")
                            .build();
        kenntnis.addStudent(student);
        kenntnisseRepository.save(kenntnis);

        student.addKenntnis(kenntnis);
        assertTrue(student.getKenntnisse().contains(kenntnis));

        student.removeKenntnis(kenntnis);
        assertFalse(student.getKenntnisse().contains(kenntnis));
        assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));
    }

    @Test
    public void testAddAndRemoveQualifikationen() {
        Qualifikation qualifikation = Qualifikation.builder()
                                      .beschreibung(BESCHREIBUNG)
                                      .build();
        qualifikation.setStudent(student);

        student.addQualifikation(qualifikation);
        assertTrue(student.getQualifikationen().contains(qualifikation));

        student.removeQualifikation(qualifikation);
        assertFalse(student.getQualifikationen().contains(qualifikation));
    }

    @Test
    public void testAddAndRemoveSprachen() {
        Sprache sprache = Sprache.builder()
                          .bezeichnung("Deutsch")
                          .level("Muttersprache")
                          .build();
        sprache.addStudent(student);
        sprachenRepository.save(sprache);

        student.addSprache(sprache);
        assertTrue(student.getSprachen().contains(sprache));

        student.removeSprache(sprache);
        assertFalse(student.getSprachen().contains(sprache));
        assertTrue(sprachenRepository.existsById(sprache.getId()));
    }

    @Test
    public void testAddAndRemoveTaetigkeitsfelder() {
        Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                                          .bezeichnung("Softwareentwicklung")
                                          .build();
        taetigkeitsfeld.addStudent(student);
        taetigkeitsfeldRepository.save(taetigkeitsfeld);

        student.addTaetigkeitsfeld(taetigkeitsfeld);
        assertTrue(student.getTaetigkeitsfelder().contains(taetigkeitsfeld));

        student.removeTaetigkeitsfeld(taetigkeitsfeld);
        assertFalse(student.getTaetigkeitsfelder().contains(taetigkeitsfeld));
        assertTrue(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));
    }

    @Test
    public void testAddandRemoveBewerbung(){
        User userUnternehmen = User.builder()
                .userid("usernameUnternehmen")
                .password("password12345")
                .email("test123Unternehmen@gmail.com")
                .build();
        userRepository.save(userUnternehmen);
        Unternehmen unternehmen = Unternehmen.builder()
                                    .name("Test GmbH")
                                    .user(userUnternehmen)
                                    .build();
        unternehmenRepository.save(unternehmen);
        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                                        .bezeichnung("Werkstudent")
                                        .beschreibung(BESCHREIBUNG)
                                        .beschaeftigungsverhaeltnis("Werkstudent")
                                        .start(LocalDate.now())
                                        .ende(LocalDate.of(2026, 12, 31))
                                        .build();
        stellenanzeige.setUnternehmen(unternehmen);
        stellenanzeigeRepository.save(stellenanzeige);

        Bewerbung bewerbung = Bewerbung.builder()
                              .student(student)
                              .stellenanzeige(stellenanzeige)
                              .build();
        bewerbungRepository.save(bewerbung);
        student.addBewerbung(bewerbung);

        assertTrue(student.getBewerbungen().contains(bewerbung));
        assertEquals(student.getBewerbungen().get(0).getStellenanzeige().getBezeichnung(), stellenanzeige.getBezeichnung());

        student.removeBewerbung(bewerbung);
        stellenanzeige.removeBewerbung(bewerbung);
        bewerbungRepository.delete(bewerbung);

        assertFalse(student.getBewerbungen().contains(bewerbung));


        Optional<Unternehmen> wrapperUnternehmen = unternehmenRepository.findById(unternehmen.getId());
        Unternehmen unternehmenFound = null;
        if(wrapperUnternehmen.isPresent()){
            unternehmenFound = wrapperUnternehmen.get();

        }
        assert unternehmenFound != null;


    }
}
