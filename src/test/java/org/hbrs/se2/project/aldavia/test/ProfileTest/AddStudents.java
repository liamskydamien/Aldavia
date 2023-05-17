package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.RolleRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AddStudents {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RolleRepository rolleRepository;


    @Test
    public void testAddStudents() {

        Rolle rolle = new Rolle("superuser");
        List<Rolle> rollen = new ArrayList<>();
        rollen.add(rolle);
        rolleRepository.save(rolle);

        User user = new User();
        user.setUserid("TestUser12");
        user.setPassword("test");
        user.setEmail("test12@test.de");
        user.setRoles(rollen);

        userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setVorname("Test");
        student.setNachname("Test");
        student.setMatrikelNummer("123467");
        student.setStudiengang("Test");
        student.setStudienbeginn(LocalDate.now());
        student.setGeburtsdatum(LocalDate.now());

        studentRepository.save(student);
    }
}
