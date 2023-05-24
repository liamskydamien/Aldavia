package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.*;

import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTest {
    //TODO: Fix this test
    //TODO: Add round trip test for Student
    //TODO: Test Constraints if student gets deleted (cascade) -> User, Qualification get deleted too. Sprache, Kenntnisse, Taetigkeitsfeld not
    //TODO: Test add... and remove... methods

        @Autowired
        private StudentRepository studentRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private KenntnisseRepository kenntnisseRepository;
        @Autowired
        private QualifikationRepository qualifikationRepository;
        @Autowired
        private SprachenRepository sprachenRepository;
        @Autowired
        private TaetigkeitsfeldRepository taetigkeitsfeldRepository;
        @Autowired
        private BewerbungRepository bewerbungRepository;
        @Autowired
        private StellenanzeigeRepsitory stellenanzeigeRepository;
        @Autowired
        private UnternehmenRepository unternehmenRepository;

        @Test
        public void testStudentRoundTrip() {
            Student student = new Student();


            student.setNachname("Smith");
            student.setVorname("John");
            student.setMatrikelNummer("123456");
            student.setStudiengang("Computer Science");
            student.setStudienbeginn(LocalDate.of(2018, 1, 1));
            student.setGeburtsdatum(LocalDate.of(2000, 1, 1));
            student.setLebenslauf("My life story");

            User user = User.builder()
                    .email("test@test.de")
                    .userid("test_user")
                    .password("test")
                    .build();
            student.setUser(user);

            Kenntnis kenntnis = Kenntnis.builder()
                    .bezeichnung("Java")
                    .build();
            student.addKenntnis(kenntnis);

            Qualifikation qualifikation = Qualifikation.builder()
                    .bezeichnung("Bachelor")
                    .build();
            student.addQualifikation(qualifikation);

            Sprache sprache = Sprache.builder()
                    .bezeichnung("English")
                    .level("C2")
                    .build();
            student.addSprache(sprache);

            Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                    .bezeichnung("Software Development")
                    .build();
            student.addTaetigkeitsfeld(taetigkeitsfeld);



            User userUnternehmen = User.builder()
                    .email("unternehmen@info.de")
                    .userid("unternehmen")
                    .password("123")
                    .build();

            Unternehmen unternehmen = Unternehmen.builder()
                    .name("Test GmbH")
                    .user(userUnternehmen)
                    .build();
            unternehmenRepository.save(unternehmen);

            Taetigkeitsfeld taetigkeitsfeldStellenanzeige = Taetigkeitsfeld.builder()
                    .bezeichnung("Software Development in Java")
                    .build();

            // Save the student to the database
            studentRepository.save(student);

            Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                    .bezeichnung("Werkstudent inSoftware Developer")
                    .beschreibung("Wir suchen einen Werkstudenten in Software Development")
                    .beschaeftigungsverhaeltnis("Werkstudent")
                    .start(LocalDate.of(2021, 1, 1))
                    .ende(LocalDate.of(2021, 12, 31))
                    .build();
            stellenanzeige.addTaetigkeitsfeld(taetigkeitsfeldStellenanzeige);
            stellenanzeige.setUnternehmen(unternehmen);



            stellenanzeigeRepository.save(stellenanzeige);

            Bewerbung bewerbung = Bewerbung.builder()
                    .datum(LocalDate.of(2023, 1, 1))
                    .stellenanzeige(stellenanzeige)
                    .build();
            Student student1 = studentRepository.findById(student.getId()).orElse(null);
            bewerbung.setStudent(student1);
            bewerbungRepository.save(bewerbung);
            studentRepository.save(student1);


            assertTrue(studentRepository.existsById(student.getId()));
            assertTrue(userRepository.existsById(user.getId()));
            assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));
            assertTrue(qualifikationRepository.existsById(qualifikation.getId()));
            assertTrue(sprachenRepository.existsById(sprache.getId()));
            assertTrue(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));
            assertTrue(bewerbungRepository.existsById(bewerbung.getId()));
            assertTrue(stellenanzeigeRepository.existsById(stellenanzeige.getId()));


            // Retrieve the student
            Student retrievedStudent = studentRepository.findById(student.getId()).orElse(null);
            assertNotNull(retrievedStudent);
            assertEquals("Smith", retrievedStudent.getNachname());
            assertEquals(user, retrievedStudent.getUser());
            assertTrue(retrievedStudent.getKenntnisse().contains(kenntnis));
            assertTrue(retrievedStudent.getQualifikationen().contains(qualifikation));
            assertTrue(retrievedStudent.getSprachen().contains(sprache));
            assertTrue(retrievedStudent.getTaetigkeitsfelder().contains(taetigkeitsfeld));
            assertTrue(retrievedStudent.getBewerbungen().contains(bewerbung));
            assertEquals(retrievedStudent.getBewerbungen().get(0).getStellenanzeige(), stellenanzeige);

            // Modify the student, schema = "aldavia_new"
            retrievedStudent.setNachname("Johnson");
            studentRepository.save(retrievedStudent);

            // Retrieve the student again
            Student modifiedStudent = studentRepository.findById(student.getId()).orElse(null);
            assertNotNull(modifiedStudent);
            assertEquals("Johnson", modifiedStudent.getNachname());

            //Delete Kenntnis
            modifiedStudent.removeKenntnis(kenntnis);
            studentRepository.save(modifiedStudent);
            assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));
            assertFalse(modifiedStudent.getKenntnisse().contains(kenntnis));

            //Delete Qualifikation
            modifiedStudent.removeQualifikation(qualifikation);
            studentRepository.save(modifiedStudent);
            qualifikationRepository.delete(qualifikation);
            assertFalse(qualifikationRepository.existsById(qualifikation.getId()));
            assertFalse(modifiedStudent.getQualifikationen().contains(qualifikation));

            //Delete Sprache
            modifiedStudent.removeSprache(sprache);
            studentRepository.save(modifiedStudent);
            sprachenRepository.delete(sprache);
            assertFalse(sprachenRepository.existsById(sprache.getId()));
            assertFalse(modifiedStudent.getSprachen().contains(sprache));

            //Delete Taetigkeitsfeld
            String taetigkeitsfeldBezeichnung = taetigkeitsfeld.getBezeichnung();
            taetigkeitsfeldRepository.deleteById(taetigkeitsfeldBezeichnung);
            modifiedStudent.removeTaetigkeitsfeld(taetigkeitsfeld);
            studentRepository.save(modifiedStudent);
            assertFalse(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));
            assertFalse(modifiedStudent.getTaetigkeitsfelder().contains(taetigkeitsfeld));

            //Delete Bewerbung

            modifiedStudent.removeBewerbung(bewerbung);
            studentRepository.save(modifiedStudent);
            bewerbungRepository.delete(bewerbung);
            assertFalse(bewerbungRepository.existsById(bewerbung.getId()));
            assertFalse(modifiedStudent.getBewerbungen().contains(bewerbung));
            assertTrue(stellenanzeigeRepository.existsById(stellenanzeige.getId()));

            //Delete Stellenanzeige
            unternehmen.removeStellenanzeige(stellenanzeige);
            stellenanzeigeRepository.delete(stellenanzeige);
            assertFalse(stellenanzeigeRepository.existsById(stellenanzeige.getId()));

            // Delete the student
            studentRepository.delete(modifiedStudent);
            assertFalse(userRepository.existsById(user.getId()));

            //studentRepository.findByUser(user);


            assertFalse(studentRepository.existsById(student.getId()));
            assertFalse(userRepository.existsById(user.getId()));
            assertTrue(kenntnisseRepository.existsById(kenntnis.getBezeichnung()));
            assertFalse(qualifikationRepository.existsById(qualifikation.getId()));
            assertFalse(sprachenRepository.existsById(sprache.getId()));
            assertFalse(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung()));

            //Delete Unternehmen
            unternehmenRepository.delete(unternehmen);
            assertFalse(unternehmenRepository.existsById(unternehmen.getId()));


            // Try to retrieve the student again
            Student deletedStudent = studentRepository.findById(student.getId()).orElse(null);
            assertNull(deletedStudent);
        }
    }
    /*

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private KenntnisseRepository kenntnisseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QualifikationRepository qualifikationRepository;
    @Autowired
    private SprachenRepository sprachenRepository;
    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;
    @Autowired
    private StellenanzeigeRepsitory stellenanzeigeRepsitory;
    @Autowired
    private BewerbungRepository bewerbungRepository;
    @Autowired
    private UnternehmenRepository unternehmenRepository;


    @Test
    public void testStudentRoundTrip() {
        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

            //Create Student

            Student student = Student.builder()

                    .vorname("Max")
                    .nachname("Muster")
                    .matrikelNummer("12348765")
                    .studiengang("Informatik")
                    .studienbeginn(LocalDate.of(2020, 6, 1))
                    .geburtsdatum(LocalDate.of(2000, 1, 1))
                    .lebenslauf("TestLebenslauf")
                    .user(user)
                    .build();


            studentRepository.save(student);
            int userID = user.getId();
            int studentID = student.getStudentId();


            //Read user
            Optional<User> wrapper = userRepository.findById(userID);
            User awaitUser = null;
            if(wrapper.isPresent()){
                awaitUser = wrapper.get();
            }

            assert awaitUser != null;
            assertEquals(user.getId(), awaitUser.getId());
            assertEquals(user.getUserid(), awaitUser.getUserid());

            //assertNotSame(user, awaitUser);

            //Read student by id
            Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
            Student awaitStudent = null;
            if(studentWrapper.isPresent()){
                awaitStudent = studentWrapper.get();
            }
            assert awaitStudent != null;
            assertEquals(student.getStudentId(), awaitStudent.getStudentId());
            assertEquals(student.getVorname(), awaitStudent.getVorname());
            assertEquals(student.getNachname(), awaitStudent.getNachname());
            assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());
            assertEquals(student.getStudiengang(), awaitStudent.getStudiengang());
            assertEquals(student.getStudienbeginn(), awaitStudent.getStudienbeginn());
            assertEquals(student.getGeburtsdatum(), awaitStudent.getGeburtsdatum());
            assertEquals(student.getLebenslauf(), awaitStudent.getLebenslauf());

            //Update
            student.setVorname("Maximilian");
            student.setNachname("Mustermia");
            student.setMatrikelNummer("12345");
            student.setStudiengang("Design");
            student.setStudienbeginn(LocalDate.of(2021, 7, 2));
            student.setGeburtsdatum(LocalDate.of(2001, 2, 2));
            student.setLebenslauf("TestLebenslauf");
            studentRepository.save(student);

            Optional<Student> studentWrapperUpdate = studentRepository.findByUser(awaitUser);
            Student awaitStudentUpdate = null;
            if(studentWrapperUpdate.isPresent()){
            awaitStudentUpdate = studentWrapperUpdate.get();
             }
            assert awaitStudentUpdate != null;
            assertEquals(student.getStudentId(), awaitStudentUpdate.getStudentId());
            assertEquals(student.getVorname(), awaitStudentUpdate.getVorname());
            assertEquals(student.getNachname(), awaitStudentUpdate.getNachname());
            assertEquals(student.getMatrikelNummer(), awaitStudentUpdate.getMatrikelNummer());
            assertEquals(student.getStudiengang(), awaitStudentUpdate.getStudiengang());
            assertEquals(student.getStudienbeginn(), awaitStudentUpdate.getStudienbeginn());
            assertEquals(student.getGeburtsdatum(), awaitStudentUpdate.getGeburtsdatum());
            assertEquals(student.getLebenslauf(), awaitStudentUpdate.getLebenslauf());


            //Delete
            studentRepository.deleteById(studentID);
            Optional<Student> awaitDeletedStudent = studentRepository.findByUserID(String.valueOf(userID));
            assertFalse(awaitDeletedStudent.isPresent());

            //Delete
            userRepository.deleteById(userID);
            Optional<User> awaitDeletedUser = userRepository.findById(userID);
            assertFalse(awaitDeletedUser.isPresent());

    }



    @Test
    public void testStudentHasSkills(){

        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

        //Create Student
        Student student = Student.builder()
                .vorname("Max")
                .nachname("Muster")
                .matrikelNummer("12348765")
                .user(user)
                .build();

        studentRepository.save(student);
        int userID = user.getId();
        int studentID = student.getStudentId();


        //Read user
        Optional<User> wrapper = userRepository.findById(userID);
        User awaitUser = null;
        if(wrapper.isPresent()){
            awaitUser = wrapper.get();
        }

        assert awaitUser != null;
        assertEquals(user.getId(), awaitUser.getId());
        assertEquals(user.getUserid(), awaitUser.getUserid());

        //assertNotSame(user, awaitUser);

        //Read student by user
        Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
        Student awaitStudent = null;
        if(studentWrapper.isPresent()){
            awaitStudent = studentWrapper.get();
        }
        assert awaitStudent != null;
        assertEquals(student.getStudentId(), awaitStudent.getStudentId());
        assertEquals(student.getVorname(), awaitStudent.getVorname());
        assertEquals(student.getNachname(), awaitStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());


        //Create Skills

        List<Kenntnis> skills = new ArrayList<>();
        //Take input Skills and Create
        Kenntnis kenntnis1 = new Kenntnis();
        kenntnis1.setBezeichnung("Java");
        skills.add(kenntnis1);

        Kenntnis kenntnis2 = new Kenntnis();
        kenntnis2.setBezeichnung("C++");
        skills.add(kenntnis2);

        kenntnisseRepository.saveAll(skills);

        //Set Skills of student
        student.setKenntnisse(skills);
        studentRepository.save(student);

        //Read skills from student
        Optional<Student> studenWraperSkill = studentRepository.findByUser(user);
        Student awaitStudentSkill = null;
        if(studenWraperSkill.isPresent()){
            awaitStudentSkill = studenWraperSkill.get();
        }
        assert awaitStudentSkill != null;

        List<Kenntnis> awaitSkills = awaitStudentSkill.getKenntnisse();
        assertTrue(awaitSkills.contains(kenntnis1));
        assertTrue(awaitSkills.contains(kenntnis2));


        //Update Skills from student
        Kenntnis kenntnisNeu = new Kenntnis();
        kenntnisNeu.setBezeichnung("Python");
        kenntnisseRepository.save(kenntnisNeu);
        skills.add(kenntnisNeu);
        student.setKenntnisse(skills);
        studentRepository.save(student);

        //Read skills from student
        Optional<Student> wraperNewStudent = studentRepository.findByUser(user);
        Student awaitNewStudent = null;
        if(wraperNewStudent.isPresent()){
            awaitNewStudent = wraperNewStudent.get();
        }
        assert awaitNewStudent != null;
        assertTrue(awaitNewStudent.getKenntnisse().contains(kenntnis1));
        assertTrue(awaitNewStudent.getKenntnisse().contains(kenntnis2));
        assertTrue(awaitNewStudent.getKenntnisse().contains(kenntnisNeu));


        //Delete Skills from student
        skills.remove(kenntnisNeu);
        student.setKenntnisse(skills);
        studentRepository.save(student);

        Optional<Student> wrapperDeletedStudentSkill = studentRepository.findByUser(user);
        Student awaitDeletedStudentSkill = null;
        if(wrapperDeletedStudentSkill.isPresent()){
            awaitDeletedStudentSkill = wrapperDeletedStudentSkill.get();
        }
        assert awaitDeletedStudentSkill != null;
        assertTrue(awaitDeletedStudentSkill.getKenntnisse().contains(kenntnis1));
        assertTrue(awaitDeletedStudentSkill.getKenntnisse().contains(kenntnis2));
        assertFalse(awaitDeletedStudentSkill.getKenntnisse().contains(kenntnisNeu));


        skills.remove(kenntnis1);
        skills.remove(kenntnis2);
        student.setKenntnisse(skills);
        studentRepository.save(student);

        Optional<Student> wraperDeletedStudent2 = studentRepository.findByUser(user);
        Student awaitDeletedStudentSkill2 = null;
        if(wraperDeletedStudent2.isPresent()){
            awaitDeletedStudentSkill2 = wraperDeletedStudent2.get();
        }
        assert awaitDeletedStudentSkill2 != null;
        assertTrue(awaitDeletedStudentSkill2.getKenntnisse().isEmpty());
        assertFalse(awaitDeletedStudentSkill2.getKenntnisse().contains(kenntnis1));
        assertFalse(awaitDeletedStudentSkill2.getKenntnisse().contains(kenntnis2));
        assertFalse(awaitDeletedStudentSkill2.getKenntnisse().contains(kenntnisNeu));

        //Delete Skills
        kenntnisseRepository.delete(kenntnis1);
        kenntnisseRepository.delete(kenntnis2);
        kenntnisseRepository.delete(kenntnisNeu);
        assertTrue(kenntnisseRepository.findAll().isEmpty());




        //Delete Student
        studentRepository.deleteById(studentID);
        Optional<Student> awaitDeletedStudent = studentRepository.findByUserID(String.valueOf(userID));
        assertFalse(awaitDeletedStudent.isPresent());

        //Delete User
        userRepository.deleteById(userID);
        Optional<User> awaitDeletedUser = userRepository.findById(userID);
        assertFalse(awaitDeletedUser.isPresent());

    }

    @Test
    public void testStudentHasQualification(){
        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

        //Create Student
        Student student = Student.builder()
                .vorname("Max")
                .nachname("Muster")
                .matrikelNummer("12348765")
                .user(user)
                .build();

        studentRepository.save(student);
        int userID = user.getId();
        int studentID = student.getStudentId();

//Hat keine Qualifikation

        //Read user
        Optional<User> wrapper = userRepository.findById(userID);
        User awaitUser = null;
        if(wrapper.isPresent()){
            awaitUser = wrapper.get();
        }

        assert awaitUser != null;
        assertEquals(user.getId(), awaitUser.getId());
        assertEquals(user.getUserid(), awaitUser.getUserid());

        //assertNotSame(user, awaitUser);

        //Read student by user
        Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
        Student awaitStudent = null;
        if(studentWrapper.isPresent()){
            awaitStudent = studentWrapper.get();
        }
        assert awaitStudent != null;
        assertEquals(student.getStudentId(), awaitStudent.getStudentId());
        assertEquals(student.getVorname(), awaitStudent.getVorname());
        assertEquals(student.getNachname(), awaitStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());

//Hat eine Qualifikation

        //Create Qualification
        List<Qualifikation> qualifikationen = new ArrayList<>();

        //Take input Qualification and Create
        Qualifikation qualifikation1 = new Qualifikation();
        qualifikation1.setBezeichnung("Werkstudent Java");
        qualifikation1.setBeschaeftigungsart("Werkstudent");
        qualifikation1.setBereich("Softwareentwicklung");
        qualifikation1.setBeschreibung("Ich war für die Entwicklung von Software zuständig.");
        qualifikationen.add(qualifikation1);

        //Save Qualification
        qualifikationRepository.save(qualifikation1);



        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);

        //Read Qualification from student
        Optional<Student> wrapperStudent = studentRepository.findByUser(user);
        Student awaitStudentQualification = null;
        if(wrapperStudent.isPresent()){
            awaitStudentQualification = wrapperStudent.get();
        }
        assert awaitStudentQualification != null;
        List<Qualifikation> awaitQualifikationen = awaitStudentQualification.getQualifikationen();
        assertTrue(awaitQualifikationen.contains(qualifikation1));

//Hat mehrere Qualifikationen
        //Update Qualification from student
        Qualifikation qualifikation2 = new Qualifikation();
        qualifikation2.setBezeichnung("Praktikum in Design");
        qualifikation2.setBeschaeftigungsart("Praktikum");
        qualifikationen.add(qualifikation2);
        student.setQualifikationen(qualifikationen);

        //Save Qualification
        qualifikationRepository.save(qualifikation2);
        studentRepository.save(student);

        //Read Qualification from student
        Optional<Student> wrapperStudent2 = studentRepository.findByUser(user);
        Student awaitStudentQualification2 = null;
        if(wrapperStudent2.isPresent()){
            awaitStudentQualification2 = wrapperStudent2.get();
        }
        assert awaitStudentQualification2 != null;
        List<Qualifikation> awaitQualifikationen2 = awaitStudentQualification2.getQualifikationen();
        assertTrue(awaitQualifikationen2.contains(qualifikation1));
        assertTrue(awaitQualifikationen2.contains(qualifikation2));

        //Delete Qualification from student
        qualifikationen.remove(qualifikation1);
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);

        //Read Qualification from student
        Optional<Student> wrapperDeletedStudentQuali = studentRepository.findByUser(user);
        Student awaitDeletedStudentQualification = null;
        if(wrapperDeletedStudentQuali.isPresent()){
            awaitDeletedStudentQualification = wrapperDeletedStudentQuali.get();
        }
        assert awaitDeletedStudentQualification != null;
        List<Qualifikation> awaitQualifikationen3 = awaitDeletedStudentQualification.getQualifikationen();
        assertFalse(awaitQualifikationen3.contains(qualifikation1));
        assertTrue(awaitQualifikationen3.contains(qualifikation2));

        //Delete Qualification from student
        qualifikationen.remove(qualifikation2);
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);

        //Read Qualification from student
        Optional<Student> wrapperDeletedStudentQuali2 = studentRepository.findByUser(user);
        Student awaitDeletedStudentQualification2 = null;
        if(wrapperDeletedStudentQuali2.isPresent()){
            awaitDeletedStudentQualification2 = wrapperDeletedStudentQuali2.get();
        }
        assert awaitDeletedStudentQualification2 != null;
        List<Qualifikation> awaitQualifikationen4 = awaitDeletedStudentQualification2.getQualifikationen();
        assertFalse(awaitQualifikationen4.contains(qualifikation1));
        assertFalse(awaitQualifikationen4.contains(qualifikation2));

        //Delete Qualification
        qualifikationRepository.delete(qualifikation1);
        qualifikationRepository.delete(qualifikation2);
        assertTrue(qualifikationRepository.findAll().isEmpty());

        //Delete Student
        studentRepository.deleteById(studentID);
        Optional<Student> awaitDeletedStudent = studentRepository.findByUser(user);
        assertFalse(awaitDeletedStudent.isPresent());

        //Delete User
        userRepository.deleteById(userID);
        Optional<User> awaitDeletedUser = userRepository.findById(userID);
        assertFalse(awaitDeletedUser.isPresent());

    }

    @Test
    public void testStudentHasLanguages(){
        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

        //Create Student
        Student student = Student.builder()
                .vorname("Max")
                .nachname("Muster")
                .matrikelNummer("12348765")
                .user(user)
                .build();

        studentRepository.save(student);
        int userID = user.getId();
        int studentID = student.getStudentId();

//Hat keine Sprache

        //Read user
        Optional<User> wrapper = userRepository.findById(userID);
        User awaitUser = null;
        if(wrapper.isPresent()){
            awaitUser = wrapper.get();
        }

        assert awaitUser != null;
        assertEquals(user.getId(), awaitUser.getId());
        assertEquals(user.getUserid(), awaitUser.getUserid());

        //assertNotSame(user, awaitUser);

        //Read student by user
        Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
        Student awaitStudent = null;
        if(studentWrapper.isPresent()){
            awaitStudent = studentWrapper.get();
        }
        assert awaitStudent != null;
        assertEquals(student.getStudentId(), awaitStudent.getStudentId());
        assertEquals(student.getVorname(), awaitStudent.getVorname());
        assertEquals(student.getNachname(), awaitStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());

//Hat eine Sprache

        //Create Language
        List<Sprache> sprachen = new ArrayList<>();

        //Take input Language and Create
        Sprache sprache1 = new Sprache();
        sprache1.setName("Deutsch");
        sprache1.setLevel("Muttersprache");
        sprachen.add(sprache1);

        //Save Language
        sprachenRepository.save(sprache1);

        student.setSprachen(sprachen);
        studentRepository.save(student);

        //Read Language from student
        Optional<Student> wrapperStudent = studentRepository.findByUser(user);
        Student awaitStudentLanguage = null;
        if(wrapperStudent.isPresent()){
            awaitStudentLanguage = wrapperStudent.get();
        }
        assert awaitStudentLanguage != null;
        List<Sprache> awaitSprachen = awaitStudentLanguage.getSprachen();
        assertTrue(awaitSprachen.contains(sprache1));

//Hat mehrere Sprachen

        //Update Language from student
        Sprache sprache2 = new Sprache();
        sprache2.setName("Englisch");
        sprache2.setLevel("B2");
        sprachen.add(sprache2);
        student.setSprachen(sprachen);

        //Save Language
        sprachenRepository.save(sprache2);
        studentRepository.save(student);

        //Read Language from student
        Optional<Student> wrapperStudent2 = studentRepository.findByUser(user);
        Student awaitStudentLanguage2 = null;
        if(wrapperStudent2.isPresent()){
            awaitStudentLanguage2 = wrapperStudent2.get();
        }
        assert awaitStudentLanguage2 != null;
        List<Sprache> awaitSprachen2 = awaitStudentLanguage2.getSprachen();
        assertTrue(awaitSprachen2.contains(sprache1));
        assertTrue(awaitSprachen2.contains(sprache2));

        //Delete Language from student
        sprachen.remove(sprache1);
        student.setSprachen(sprachen);
        studentRepository.save(student);

        //Read Language from student
        Optional<Student> wrapperDeletedStudentLang = studentRepository.findByUser(user);
        Student awaitDeletedStudentLanguage = null;
        if(wrapperDeletedStudentLang.isPresent()){
            awaitDeletedStudentLanguage = wrapperDeletedStudentLang.get();
        }
        assert awaitDeletedStudentLanguage != null;
        List<Sprache> awaitSprachen3 = awaitDeletedStudentLanguage.getSprachen();
        assertFalse(awaitSprachen3.contains(sprache1));
        assertTrue(awaitSprachen3.contains(sprache2));

        //Delete Language from student
        sprachen.remove(sprache2);
        student.setSprachen(sprachen);
        studentRepository.save(student);

        //Read Language from student
        Optional<Student> wrapperDeletedStudentLang2 = studentRepository.findByUser(user);
        Student awaitDeletedStudentLanguage2 = null;
        if(wrapperDeletedStudentLang2.isPresent()){
            awaitDeletedStudentLanguage2 = wrapperDeletedStudentLang2.get();
        }
        assert awaitDeletedStudentLanguage2 != null;
        List<Sprache> awaitSprachen4 = awaitDeletedStudentLanguage2.getSprachen();
        assertFalse(awaitSprachen4.contains(sprache1));
        assertFalse(awaitSprachen4.contains(sprache2));

        //Delete Language
        sprachenRepository.delete(sprache1);
        sprachenRepository.delete(sprache2);
        assertTrue(sprachenRepository.findAll().isEmpty());

        //Delete Student
        studentRepository.deleteById(studentID);
        Optional<Student> awaitDeletedStudent = studentRepository.findByUser(user);
        assertFalse(awaitDeletedStudent.isPresent());

        //Delete User
        userRepository.deleteById(userID);
        Optional<User> awaitDeletedUser = userRepository.findById(userID);
        assertFalse(awaitDeletedUser.isPresent());

    }

    @Test
    public void testStudentHasTaetigkeitsfeld(){
        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

        //Create Student
        Student student = Student.builder()
                .vorname("Max")
                .nachname("Muster")
                .matrikelNummer("12348765")
                .user(user)
                .build();


        studentRepository.save(student);
        int userID = user.getId();
        int studentID = student.getStudentId();

//Hat interessiert sich für kein Taetigkeitsfeld

        //Read user
        Optional<User> wrapper = userRepository.findById(userID);
        User awaitUser = null;
        if(wrapper.isPresent()){
            awaitUser = wrapper.get();
        }

        assert awaitUser != null;
        assertEquals(user.getId(), awaitUser.getId());
        assertEquals(user.getUserid(), awaitUser.getUserid());

        //assertNotSame(user, awaitUser);

        //Read student by user
        Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
        Student awaitStudent = null;
        if(studentWrapper.isPresent()){
            awaitStudent = studentWrapper.get();
        }
        assert awaitStudent != null;
        assertEquals(student.getStudentId(), awaitStudent.getStudentId());
        assertEquals(student.getVorname(), awaitStudent.getVorname());
        assertEquals(student.getNachname(), awaitStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());

//Hat interessiert sich für ein Taetigkeitsfeld

        //Create Taetigkeitsfeld
        List<Taetigkeitsfeld> taetigkeitsfelder = new ArrayList<>();

        //Take input Taetigkeitsfeld and Create
        Taetigkeitsfeld taetigkeitsfeld1 = new Taetigkeitsfeld();
        taetigkeitsfeld1.setBezeichnung("IT");
        taetigkeitsfelder.add(taetigkeitsfeld1);

        //Save Taetigkeitsfeld
        taetigkeitsfeldRepository.save(taetigkeitsfeld1);

        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);

        //Read Taetigkeitsfeld from student
        Optional<Student> wrapperStudent = studentRepository.findByUser(user);
        Student awaitStudentTaetigkeitsfeld = null;
        if(wrapperStudent.isPresent()){
            awaitStudentTaetigkeitsfeld = wrapperStudent.get();
        }
        assert awaitStudentTaetigkeitsfeld != null;
        List<Taetigkeitsfeld> awaitTaetigkeitsfelder = awaitStudentTaetigkeitsfeld.getTaetigkeitsfelder();
        assertTrue(awaitTaetigkeitsfelder.contains(taetigkeitsfeld1));

//Hat mehrere Taetigkeitsfelder

        //Update Taetigkeitsfeld from student
        Taetigkeitsfeld taetigkeitsfeld2 = new Taetigkeitsfeld();
        taetigkeitsfeld2.setBezeichnung("Design");
        taetigkeitsfelder.add(taetigkeitsfeld2);
        student.setTaetigkeitsfelder(taetigkeitsfelder);

        //Save Taetigkeitsfeld
        taetigkeitsfeldRepository.save(taetigkeitsfeld2);
        studentRepository.save(student);

        //Read Taetigkeitsfeld from student
        Optional<Student> wrapperStudent2 = studentRepository.findByUser(user);
        Student awaitStudentTaetigkeitsfeld2 = null;
        if(wrapperStudent2.isPresent()){
            awaitStudentTaetigkeitsfeld2 = wrapperStudent2.get();
        }
        assert awaitStudentTaetigkeitsfeld2 != null;
        List<Taetigkeitsfeld> awaitTaetigkeitsfelder2 = awaitStudentTaetigkeitsfeld2.getTaetigkeitsfelder();
        assertTrue(awaitTaetigkeitsfelder2.contains(taetigkeitsfeld1));
        assertTrue(awaitTaetigkeitsfelder2.contains(taetigkeitsfeld2));

        // Delete Taetigkeitsfeld from student

        taetigkeitsfelder.remove(taetigkeitsfeld1);
        taetigkeitsfelder.remove(taetigkeitsfeld2);
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);

        //Read Taetigkeitsfeld from student
        Optional<Student> wrapperStudent3 = studentRepository.findByUser(user);
        Student awaitStudentTaetigkeitsfeld3 = null;
        if(wrapperStudent3.isPresent()){
            awaitStudentTaetigkeitsfeld3 = wrapperStudent3.get();
        }
        assert awaitStudentTaetigkeitsfeld3 != null;
        List<Taetigkeitsfeld> awaitTaetigkeitsfelder3 = awaitStudentTaetigkeitsfeld3.getTaetigkeitsfelder();
        assertFalse(awaitTaetigkeitsfelder3.contains(taetigkeitsfeld1));
        assertFalse(awaitTaetigkeitsfelder3.contains(taetigkeitsfeld2));

        //Delete Taetigkeitsfeld
        taetigkeitsfeldRepository.delete(taetigkeitsfeld1);
        taetigkeitsfeldRepository.delete(taetigkeitsfeld2);
        assertTrue(taetigkeitsfeldRepository.findAll().isEmpty());

        //Delete Student
        studentRepository.deleteById(studentID);
        Optional<Student> awaitDeletedStudent = studentRepository.findByUser(user);
        assertFalse(awaitDeletedStudent.isPresent());

        //Delete User
        userRepository.deleteById(userID);
        Optional<User> awaitDeletedUser = userRepository.findById(userID);
        assertFalse(awaitDeletedUser.isPresent());

    }

    @Test
    public void testStudentfavorStellenanzeige(){
        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

        //Create Student
        Student student = Student.builder()
                .vorname("Max")
                .nachname("Muster")
                .matrikelNummer("12348765")
                .user(user)
                .build();


        studentRepository.save(student);
        int userID = user.getId();
        int studentID = student.getStudentId();

//Hat keine favorisierten Stellenanzeigen

        //Read user
        Optional<User> wrapper = userRepository.findById(userID);
        User awaitUser = null;
        if(wrapper.isPresent()){
            awaitUser = wrapper.get();
        }

        assert awaitUser != null;
        assertEquals(user.getId(), awaitUser.getId());
        assertEquals(user.getUserid(), awaitUser.getUserid());

        //assertNotSame(user, awaitUser);

        //Read student by user
        Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
        Student awaitStudent = null;
        if(studentWrapper.isPresent()){
            awaitStudent = studentWrapper.get();
        }
        assert awaitStudent != null;
        assertEquals(student.getStudentId(), awaitStudent.getStudentId());
        assertEquals(student.getVorname(), awaitStudent.getVorname());
        assertEquals(student.getNachname(), awaitStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());

        //assertNotSame(student, awaitStudent);

        //Create Stellenanzeige
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        stellenanzeige.setBezeichnung("Softwareentwickler");
        stellenanzeige.setBeschreibung("Entwickeln von Software");
        stellenanzeigeRepsitory.save(stellenanzeige);
        int stellenanzeigeID = stellenanzeige.getStellenanzeigeId();

        Stellenanzeige stellenanzeige2 = new Stellenanzeige();
        stellenanzeige2.setBezeichnung("Designer");
        stellenanzeige2.setBeschreibung("Designen von Produkten");
        stellenanzeigeRepsitory.save(stellenanzeige2);
        int stellenanzeigeID2 = stellenanzeige2.getStellenanzeigeId();

  //Student hat eine favorisierte Stellenanzeige

        //Favorisiere Stellenanzeigen
        List<Stellenanzeige> favorisierteStellenanzeigen = new ArrayList<>();
        favorisierteStellenanzeigen.add(stellenanzeige);
        student.setStellenanzeigen(favorisierteStellenanzeigen);
        studentRepository.save(student);

        //Read favorisierte Stellenanzeigen from student
        Optional<Student> wrapperStudent = studentRepository.findByUser(user);
        Student awaitStudentFavorisierteStellenanzeigen = null;
        if(wrapperStudent.isPresent()){
            awaitStudentFavorisierteStellenanzeigen = wrapperStudent.get();
        }
        assert awaitStudentFavorisierteStellenanzeigen != null;
        List<Stellenanzeige> awaitFavorisierteStellenanzeigen = awaitStudentFavorisierteStellenanzeigen.getStellenanzeigen();
        assertTrue(awaitFavorisierteStellenanzeigen.contains(stellenanzeige));
        assertFalse(awaitFavorisierteStellenanzeigen.contains(stellenanzeige2));

//Student hat mehrere favorisierte Stellenanzeigen
        //Update favorisierte Stellenanzeigen
        favorisierteStellenanzeigen.add(stellenanzeige2);
        student.setStellenanzeigen(favorisierteStellenanzeigen);
        studentRepository.save(student);

        //Read favorisierte Stellenanzeigen from student
        Optional<Student> wrapperStudent2 = studentRepository.findByUser(user);
        Student awaitStudentFavorisierteStellenanzeigen2 = null;
        if(wrapperStudent2.isPresent()){
            awaitStudentFavorisierteStellenanzeigen2 = wrapperStudent2.get();
        }
        assert awaitStudentFavorisierteStellenanzeigen2 != null;
        List<Stellenanzeige> awaitFavorisierteStellenanzeigen2 = awaitStudentFavorisierteStellenanzeigen2.getStellenanzeigen();
        assertTrue(awaitFavorisierteStellenanzeigen2.contains(stellenanzeige));
        assertTrue(awaitFavorisierteStellenanzeigen2.contains(stellenanzeige2));

        //Delete Stellenanzeige from favorisierte Stellenanzeigen
        favorisierteStellenanzeigen.remove(stellenanzeige);
        student.setStellenanzeigen(favorisierteStellenanzeigen);
        studentRepository.save(student);

        //Read favorisierte Stellenanzeigen from student
        Optional<Student> wrapperStudent3 = studentRepository.findByUser(user);
        Student awaitStudentDeleteFavorisierteStellenanzeigen = null;
        if(wrapperStudent3.isPresent()){
            awaitStudentDeleteFavorisierteStellenanzeigen = wrapperStudent3.get();
        }
        assert awaitStudentDeleteFavorisierteStellenanzeigen != null;
        List<Stellenanzeige> awaitDeletedFavorisierteStellenanzeigen = awaitStudentDeleteFavorisierteStellenanzeigen.getStellenanzeigen();
        assertFalse(awaitDeletedFavorisierteStellenanzeigen.contains(stellenanzeige));
        assertTrue(awaitDeletedFavorisierteStellenanzeigen.contains(stellenanzeige2));

        //Delete Stellenanzeige from favorisierte Stellenanzeigen
        favorisierteStellenanzeigen.remove(stellenanzeige2);
        student.setStellenanzeigen(favorisierteStellenanzeigen);
        studentRepository.save(student);

        assertTrue(favorisierteStellenanzeigen.isEmpty());
        assertTrue(student.getStellenanzeigen().isEmpty());

        //Delete Stellenanzeige
        stellenanzeigeRepsitory.delete(stellenanzeige);
        stellenanzeigeRepsitory.delete(stellenanzeige2);
        assertTrue(stellenanzeigeRepsitory.findAll().isEmpty());

        //Delete Student
        studentRepository.deleteById(studentID);
        Optional<Student> wrapperStudent4 = studentRepository.findById(studentID);
        assertFalse(wrapperStudent4.isPresent());

        //Delete User
        userRepository.deleteById(userID);
        Optional<User> wrapperUser = userRepository.findById(userID);
        assertFalse(wrapperUser.isPresent());

    }

    @Test
    public void testStudentBewirbtSichAufStellenanzeige(){
        //Create User
        User user = User.builder()
                .userid("Benutzername123")
                .email("bla@bla.com")
                .password("1234567890")
                .build();
        userRepository.save(user);

        //Create Student
        Student student = Student.builder()
                .vorname("Max")
                .nachname("Muster")
                .matrikelNummer("12348765")
                .user(user)
                .build();


        studentRepository.save(student);
        int userID = user.getId();
        int studentID = student.getStudentId();

//Hat sich noch nicht auf eine Stellenanzeige beworben

        //Read user
        Optional<User> wrapper = userRepository.findById(userID);
        User awaitUser = null;
        if(wrapper.isPresent()){
            awaitUser = wrapper.get();
        }

        assert awaitUser != null;
        assertEquals(user.getId(), awaitUser.getId());
        assertEquals(user.getUserid(), awaitUser.getUserid());

        //assertNotSame(user, awaitUser);

        //Read student by user
        Optional<Student> studentWrapper = studentRepository.findByUser(awaitUser);
        Student awaitStudent = null;
        if(studentWrapper.isPresent()){
            awaitStudent = studentWrapper.get();
        }
        assert awaitStudent != null;
        assertEquals(student.getStudentId(), awaitStudent.getStudentId());
        assertEquals(student.getVorname(), awaitStudent.getVorname());
        assertEquals(student.getNachname(), awaitStudent.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudent.getMatrikelNummer());

        //Create Stellenanzeige
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        stellenanzeige.setBezeichnung("Softwareentwickler");
        stellenanzeige.setBeschreibung("Entwickeln von Software");
        stellenanzeigeRepsitory.save(stellenanzeige);
        int stellenanzeigeID = stellenanzeige.getStellenanzeigeId();

        Stellenanzeige stellenanzeige2 = new Stellenanzeige();
        stellenanzeige2.setBezeichnung("Designer");
        stellenanzeige2.setBeschreibung("Designen von Produkten");
        stellenanzeigeRepsitory.save(stellenanzeige2);
        int stellenanzeigeID2 = stellenanzeige2.getStellenanzeigeId();

//Student bewribt sich auf eine Stelle

        //Create Bewerbung
        Bewerbung bewerbung = new Bewerbung();
        bewerbung.setDatum(LocalDate.of(2023, 1, 1));
        bewerbung.setStudent(student);
        bewerbung.setStellenanzeige(stellenanzeige);
        bewerbungRepository.save(bewerbung);

        List<Bewerbung> bewerbungen = new ArrayList<>();
        bewerbungen.add(bewerbung);
        student.setBewerbungen(bewerbungen);
        studentRepository.save(student);

        //Bewerbung zu Stellenanzeige hinzufügen
        List<Bewerbung> StellenanzeigeBewerbung = new ArrayList<>();
        StellenanzeigeBewerbung.add(bewerbung);
        stellenanzeige.setBewerbungen(StellenanzeigeBewerbung);
        stellenanzeigeRepsitory.save(stellenanzeige);


        //Read Bewerbung from student
        Optional<Student> wrapperStudent = studentRepository.findByUser(user);
        Student awaitStudentBewerbung = null;
        if(wrapperStudent.isPresent()){
            awaitStudentBewerbung = wrapperStudent.get();
        }
        assert awaitStudentBewerbung != null;
        List<Bewerbung> awaitBewerbungen = awaitStudentBewerbung.getBewerbungen();
        assertTrue(awaitBewerbungen.contains(bewerbung));

        //Read Stellenanzeige from Bewerbung
        Stellenanzeige awaitStellenanzeige = awaitBewerbungen.get(0).getStellenanzeige();
        assertEquals(stellenanzeige.getStellenanzeigeId(), awaitStellenanzeige.getStellenanzeigeId());
        assertEquals(stellenanzeige.getBezeichnung(), awaitStellenanzeige.getBezeichnung());
        assertEquals(stellenanzeige.getBeschreibung(), awaitStellenanzeige.getBeschreibung());

        //Read Student from Bewerbung
        Student awaitStudentFromBewerbung = awaitBewerbungen.get(0).getStudent();
        assertEquals(student.getStudentId(), awaitStudentFromBewerbung.getStudentId());
        assertEquals(student.getVorname(), awaitStudentFromBewerbung.getVorname());
        assertEquals(student.getNachname(), awaitStudentFromBewerbung.getNachname());
        assertEquals(student.getMatrikelNummer(), awaitStudentFromBewerbung.getMatrikelNummer());

        //Read Bewerbung from Stellenanzeige
        Optional<Stellenanzeige> wrapperStellenanzeige = stellenanzeigeRepsitory.findById(stellenanzeigeID);
        Stellenanzeige awaitStellenanzeigeBewerbung = null;
        if(wrapperStellenanzeige.isPresent()){
            awaitStellenanzeigeBewerbung = wrapperStellenanzeige.get();
        }
        assert awaitStellenanzeigeBewerbung != null;
        List<Bewerbung> awaitBewerbungenStellenanzeige = awaitStellenanzeigeBewerbung.getBewerbungen();
        assertTrue(awaitBewerbungenStellenanzeige.contains(bewerbung));

//Student hat sich auf zwei stellen Anzeige beworben
        //Update
        Bewerbung bewerbung2 = new Bewerbung();
        bewerbung2.setDatum(LocalDate.of(2023, 2, 2));
        bewerbung2.setStudent(student);
        bewerbung2.setStellenanzeige(stellenanzeige2);
        bewerbungRepository.save(bewerbung2);

        bewerbungen.add(bewerbung2);
        student.setBewerbungen(bewerbungen);
        studentRepository.save(student);

        //Bewerbung zu Stellenanzeige hinzufügen
        List<Bewerbung> Stellenanzeige2Bewerbung = new ArrayList<>();
        Stellenanzeige2Bewerbung.add(bewerbung2);
        stellenanzeige2.setBewerbungen(Stellenanzeige2Bewerbung);
        stellenanzeigeRepsitory.save(stellenanzeige2);

        //Read Bewerbung from student
        Optional<Student> wrapperStudent2 = studentRepository.findByUser(user);
        Student awaitStudentBewerbung2 = null;
        if(wrapperStudent2.isPresent()){
            awaitStudentBewerbung2 = wrapperStudent2.get();
        }
        assert awaitStudentBewerbung2 != null;
        List<Bewerbung> awaitBewerbungen2 = awaitStudentBewerbung2.getBewerbungen();
        assertTrue(awaitBewerbungen2.contains(bewerbung2));
        assertTrue(awaitBewerbungen2.contains(bewerbung));

        //Delete Bewerbung from student
        bewerbungen.remove(bewerbung);
        student.setBewerbungen(bewerbungen);
        studentRepository.save(student);

        //Delete Bewerbung from Stellenanzeige
        StellenanzeigeBewerbung.remove(bewerbung);
        stellenanzeige.setBewerbungen(StellenanzeigeBewerbung);
        stellenanzeigeRepsitory.save(stellenanzeige);

        //Read Bewerbung from student
        Optional<Student> wrapperStudent3 = studentRepository.findByUser(user);
        Student awaitStudentDeleteBewerbung = null;
        if(wrapperStudent3.isPresent()){
            awaitStudentDeleteBewerbung = wrapperStudent3.get();
        }
        assert awaitStudentDeleteBewerbung != null;
        List<Bewerbung> awaitBewerbungenAfterDelete = awaitStudentDeleteBewerbung.getBewerbungen();
        assertFalse(awaitBewerbungenAfterDelete.contains(bewerbung));
        assertTrue(awaitBewerbungenAfterDelete.contains(bewerbung2));

        //Read Bewerbung from Stellenanzeige
        Optional<Stellenanzeige> wrapperStellenanzeige2 = stellenanzeigeRepsitory.findById(stellenanzeigeID);
        Stellenanzeige awaitStellenanzeigeDeleteBewerbung = null;
        if(wrapperStellenanzeige2.isPresent()){
            awaitStellenanzeigeDeleteBewerbung = wrapperStellenanzeige2.get();
        }
        assert awaitStellenanzeigeDeleteBewerbung != null;
        List<Bewerbung> awaitBewerbungenStellenanzeigeAfterDelete = awaitStellenanzeigeDeleteBewerbung.getBewerbungen();
        assertFalse(awaitBewerbungenStellenanzeigeAfterDelete.contains(bewerbung));
        assertFalse(awaitBewerbungenStellenanzeigeAfterDelete.contains(bewerbung2));

        //Delete Bewerbung from student
        bewerbungen.remove(bewerbung2);
        student.setBewerbungen(bewerbungen);
        studentRepository.save(student);

        //Delete Bewerbung from Stellenanzeige
        Stellenanzeige2Bewerbung.remove(bewerbung2);
        stellenanzeige2.setBewerbungen(Stellenanzeige2Bewerbung);
        stellenanzeigeRepsitory.save(stellenanzeige2);

        assertTrue(student.getBewerbungen().isEmpty());
        assertTrue(stellenanzeige2.getBewerbungen().isEmpty());

        //Delete Bewerbung von Stellenanzeige
        bewerbung.setStellenanzeige(null);
        bewerbungRepository.save(bewerbung);
        bewerbung2.setStellenanzeige(null);

        //Delete Bewerbung
        bewerbungRepository.delete(bewerbung);
        bewerbungRepository.delete(bewerbung2);
        assertTrue(bewerbungRepository.findAll().isEmpty());

        //Delete Stellenanzeige
        stellenanzeigeRepsitory.delete(stellenanzeige);
        stellenanzeigeRepsitory.delete(stellenanzeige2);
        assertTrue(stellenanzeigeRepsitory.findAll().isEmpty());

        //Delete Student
        studentRepository.deleteById(studentID);
        Optional<Student> awaitDeletedStudent = studentRepository.findByUser(user);
        assertFalse(awaitDeletedStudent.isPresent());

        //Delete User
        userRepository.deleteById(userID);
        Optional<User> awaitDeletedUser = userRepository.findById(userID);
        assertFalse(awaitDeletedUser.isPresent());

    }


*/






