package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.control.LoginControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
public class DatabaseUserExceptionTest {

    @Test
    void testGetDatabaseUserExceptionType() {
        DatabaseUserException exception = new DatabaseUserException(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, "User not found");
        Assertions.assertEquals(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, exception.getDatabaseUserExceptionType());
    }
    //Test ob DatabaseUserExceptionType so funktioniert wie es soll

    @Test
    void testGetReason() {
        String reason = "No User could be found! Please check your credentials!";
        DatabaseUserException exception = new DatabaseUserException(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, reason);
        Assertions.assertEquals(reason, exception.getReason());
    }
    //Test ob GetReason funktioniert wie es funktionieren soll

    private LoginControl loginControl;  //Es folgt Exceptiontest mit Login-Controll

    @BeforeEach
    void setUp() {
        loginControl = new LoginControl();
    }

    /*
    @Test
    void testAuthenticateUserNotFound() {
        String username = "nonexistentUser";
        String password = "password123";

        DatabaseUserException exception = Assertions.assertThrows(DatabaseUserException.class, () ->
                loginControl.authenticate(username, password));

        Assertions.assertEquals(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, exception.getDatabaseUserExceptionType());
        Assertions.assertEquals("No User could be found! Please check your credentials!", exception.getMessage());
    }

     */
}
