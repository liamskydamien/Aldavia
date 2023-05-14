package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AddStudents {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Test
    public void testAddStudents() {
        User user = new User();
        user.setUserid("test");
        user.setPassword("test");
        user.setEmail("test@test.de");

        userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setVorname("Test");
        student.setNachname("Test");
        student.setMatrikelNummer("123456");
        student.setStudiengang("Test");
        student.setStudienbeginn(LocalDate.now());
        student.setGeburtsdatum(LocalDate.now());

        studentRepository.save(student);
    }
}
