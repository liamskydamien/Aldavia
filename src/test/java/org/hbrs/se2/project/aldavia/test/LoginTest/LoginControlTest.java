package org.hbrs.se2.project.aldavia.test.LoginTest;

import org.hbrs.se2.project.aldavia.control.LoginControl;
import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.RolleRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControlTest {
    @Autowired
    private LoginControl loginControl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolleRepository rolleRepository;

    private final UserDTOImpl testUser = new UserDTOImpl();

    @BeforeAll
    public void setUp() {

        Rolle rolle = new Rolle();
        rolle.setBezeichnung("Tester");
        List<Rolle> rollen = new ArrayList<>();
        rolleRepository.save(rolle);
        rollen.add(rolle);

        User user = new User();
        user.setUserid("sascha");
        user.setPassword("abc");
        user.setEmail("test@aldavia.de");
        user.setRoles(rollen);
        userRepository.save(user);

        testUser.setUserid("sascha");
        testUser.setPassword("abc");
        testUser.setEmail("test@aldavia.de");
    }

    @AfterAll
    public void tearDown() {
        try {
            Optional<User> user = userRepository.findUserByUseridAndPassword("sascha", "abc");
            userRepository.deleteById(user.get().getId());
            Optional<Rolle> rolle = rolleRepository.findByBezeichnung("Tester");
            rolleRepository.deleteById(rolle.get().getBezeichnung());
        }
        catch (Exception e) {
            System.out.println("User not found");
        }
    }

    @Test
    @Transactional
    public void testLoginPositiv() {
        try {
            boolean userIsThere = loginControl.authenticate("sascha", "abc");
            assertTrue(userIsThere);

            boolean userIsThere2 = loginControl.authenticate("test@aldavia.de", "abc");
            assertTrue(userIsThere2);
            assertEquals(loginControl.getCurrentUser().getUserid(), testUser.getUserid(), "Userid not equal");
        }
        catch (DatabaseUserException e) {
            assertEquals(e.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "User not found");
        }
    }

    @Test
    public void testLoginNegativ() {
        DatabaseUserException exceptionWrongPassword = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authenticate("sascha", "abcd");
        });

        DatabaseUserException exceptionWrongUsername = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authenticate("saschaa", "abc");
        });

        DatabaseUserException exceptionWrongEmail = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authenticate("test2@aldavia.de", "abc");
        });

        assertEquals(exceptionWrongPassword.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "Wrong Exception thrown");
        assertEquals(exceptionWrongUsername.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "Wrong Exception thrown");
        assertEquals(exceptionWrongEmail.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "Wrong Exception thrown");
    }

    /**
    @Test
    public void testGetUserDTO() {
        try {
            UserDTO userDTO = loginControl.TestGetUserWithJPA("sascha", "abc");
            assertEquals(userDTO.getFirstName(), testUser.getFirstName(), "Firstname not equal");
            assertEquals(userDTO.getLastName(), testUser.getLastName(), "Lastname not equal");
        }
        catch (DatabaseUserException e) {
            assertEquals(e.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "User not found");
        }
    }
    */



}
