package org.hbrs.se2.project.aldavia.test.RegistrationTest;

import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegistrationTest {

    @Autowired
    UserRepository userRepository;


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UnternehmenRepository unternehmenRepository;

    @Test
    public void testUser() {
        User user = userRepository.findUserByEmail("123").get();
        assertEquals("t", user.getUserid());

    }







}
