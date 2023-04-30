package org.hbrs.se2.project.aldavia.test.LoginTest;

import org.hbrs.se2.project.aldavia.control.LoginControl;
import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.UserDTOImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginControlTest {
    @Autowired
    private LoginControl loginControl;

    private static UserDTOImpl testUser = new UserDTOImpl();

    @BeforeAll
    public static void setUp() {
        testUser.setFirstname("Sascha");
        testUser.setLastname("Alda");
    }

    @Test
    public void testLoginPositiv() {
        try {
            boolean userIsThere = loginControl.authentificate("sascha", "abc");
            assertTrue(userIsThere);
        }
        catch (DatabaseUserException e) {
            assertEquals(e.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "User not found");
        }
    }

    @Test
    public void testLoginNegativ() {
        DatabaseUserException exceptionWrongPassword = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authentificate("sascha", "abcd");
        });

        DatabaseUserException exceptionWrongUsername = assertThrows(
                DatabaseUserException.class, () -> {
                    loginControl.authentificate("saschaa", "abc");
        });

        assertEquals(exceptionWrongPassword.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "Wrong Exception thrown");
        assertEquals(exceptionWrongUsername.getDatabaseUserExceptionType(), DatabaseUserException.DatabaseUserExceptionType.UserNotFound, "Wrong Exception thrown");
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
