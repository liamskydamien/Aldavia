package org.hbrs.se2.project.aldavia.test.LoginTest;

import org.hbrs.se2.project.aldavia.control.LoginControl;
import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
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
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControlTest {
    public static final String USERID = "sascha";
    public static final String PASSWORD = "abc";
    public static final String EMAIL = "test@aldavia.de";
    public static final String ROLLE = "Tester";
    public static final String MESSAGE = "Wrong Exception thrown";
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
        rolle.setBezeichnung(ROLLE);
        List<Rolle> rollen = new ArrayList<>();
        rolleRepository.save(rolle);
        rollen.add(rolle);

        User user = new User();
        user.setUserid(USERID);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setRollen(rollen);
        userRepository.save(user);

        testUser.setUserid(USERID);
        testUser.setPassword(PASSWORD);
        testUser.setEmail(EMAIL);
    }

    @AfterAll
    public void tearDown() {
        Optional<User> user = userRepository.findUserByUseridAndPassword(USERID, PASSWORD);
        userRepository.deleteById(user.orElseThrow().getId());
        Optional<Rolle> rolle = rolleRepository.findRolleByBezeichnung(ROLLE);
        rolleRepository.deleteById(rolle.orElseThrow().getBezeichnung());
    }

    @Test
    @Transactional
    public void testLoginPositiv() {
        try {
            boolean userIsThere = loginControl.authenticate(USERID, PASSWORD);
            assertTrue(userIsThere);

            boolean userIsThere2 = loginControl.authenticate(EMAIL, PASSWORD);
            assertTrue(userIsThere2);
            assertEquals(loginControl.getCurrentUser().getUserid(), testUser.getUserid(), "Userid not equal");
        }
        catch (DatabaseUserException e) {
            assertEquals(e.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, "User not found");
        }
    }

    @Test
    public void testLoginNegativ() {
        DatabaseUserException exceptionWrongPassword = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authenticate(USERID, "abcd");
        });

        DatabaseUserException exceptionWrongUsername = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authenticate("saschaa", PASSWORD);
        });

        DatabaseUserException exceptionWrongEmail = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authenticate("test2@aldavia.de", PASSWORD);
        });

        assertEquals(exceptionWrongPassword.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, MESSAGE);
        assertEquals(exceptionWrongUsername.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, MESSAGE);
        assertEquals(exceptionWrongEmail.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, MESSAGE);
    }

}
