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
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    @Test
    public void testUser() {
        User dbUser = new User();
        dbUser.setUserid("t");
        dbUser.setEmail("123");
        userRepository.save(dbUser);

        User user = userRepository.findUserByEmail("123").get();
        assertEquals("t", user.getUserid());

    }







}
