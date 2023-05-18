package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.K;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.RolleRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;

import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hibernate.Hibernate;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private KenntnisseRepository kenntnisseRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void testeRoundTrip() {


            //Create User
            User user = new User();
            user.setUserid("Benutzername123");

            //Create Student
            Student student = new Student();
            student.setUser(user);
            student.setVorname("Max");
            student.setNachname("Muster");
            student.setMatrikelNummer("12348765");
            student.setStudiengang("Informatik");
            student.setStudienbeginn(LocalDate.of(2020, 6, 1));
            student.setGeburtsdatum(LocalDate.of(2000, 1, 1));
            student.setLebenslauf("TestLebenslauf");


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
            Optional<User> awaitDeletedUser = userRepository.findById(userID);
            assertFalse(awaitDeletedUser.isPresent());

    }

    @Test
    public void hasSkills(){

        //Create User
        User user = new User();
        user.setUserid("Benutzername123");

        //Create Student
        Student student = new Student();
        student.setUser(user);
        student.setVorname("Max");
        student.setNachname("Muster");
        student.setMatrikelNummer("12348765");

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


        //Update Skills from student with old skill
        skills.add(kenntnisNeu);
        student.setKenntnisse(skills);
        studentRepository.save(student);

        assertTrue(awaitNewStudent.getKenntnisse().containsAll(skills));

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

        Optional<User> awaitDeletedUser = userRepository.findById(userID);
        assertFalse(awaitDeletedUser.isPresent());

    }

    @Test
    public void hasQualification(){

    }

}


