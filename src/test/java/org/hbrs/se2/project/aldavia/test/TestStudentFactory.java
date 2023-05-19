package org.hbrs.se2.project.aldavia.test;

import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestStudentFactory {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    private Student student;

    public Student createStudent(){
        student = addStudent();
        return student;
    }

    public boolean deleteStudent(){
        return deleteStudent(student);
    }

    private Student addStudent(){
        // Create User
        User user = new User();
        user.setUserid("test_user3");
        user.setPassword("test_user3");
        user.setEmail("test@test_user3.de");

        userRepository.save(user);

        // Create Student
        Student student = new Student();
        student.setVorname("Guido");
        student.setNachname("Müller");
        student.setMatrikelNummer("12345678901");
        student.setGeburtsdatum(LocalDate.of(1990, 1, 1));
        student.setStudienbeginn(LocalDate.of(2010, 1, 1));
        student.setStudiengang("Informatik");
        student.setLebenslauf("Lebenslauf");
        student.setUser(user);


        studentRepository.save(student);

        return student;
    }

    private boolean deleteStudent(Student student){
        try{
            studentRepository.delete(student);
            userRepository.delete(student.getUser());
            return true;
        }
        catch (Exception e){
            System.out.println("Fehler beim Löschen"+ e.getMessage());
            return false;
        }
    }
}
