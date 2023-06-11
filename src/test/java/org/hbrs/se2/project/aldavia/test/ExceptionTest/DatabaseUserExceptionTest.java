package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.control.LoginControl;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class DatabaseUserExceptionTest {

    @Test
    void testGetDatabaseUserExceptionType() {
        DatabaseUserException exception = new DatabaseUserException(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, "User not found");
        Assertions.assertEquals(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, exception.getDatabaseUserExceptionType());
    }
    //Test ob User-Not-Found so funktioniert wie es soll

    @Test
    void testGetDatabaseUserExceptionType2() {
        DatabaseUserException exception2 = new DatabaseUserException(DatabaseUserException.DatabaseUserExceptionType.DATABASE_CONNECTION_FAILED, "A failure occured while trying to connect to database. Please try again later.");
        Assertions.assertEquals(DatabaseUserException.DatabaseUserExceptionType.DATABASE_CONNECTION_FAILED, exception2.getDatabaseUserExceptionType());
    }
    //Test ob Database-Connection-Failed so funktioniert wie es soll

    @Test
    void testGetReason() {
        String reason = "No User could be found! Please check your credentials!";
        DatabaseUserException exception = new DatabaseUserException(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, reason);
        Assertions.assertEquals(reason, exception.getReason());
    }
    //Test ob GetReason funktioniert wie es funktionieren soll

    private LoginControl loginControl;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        loginControl = new LoginControl();
        //UserRepository repository = new UserRepository();
    }

    @Test
    void testUserNotFound(){
        String username = "gibtsNicht";
        String password = "password1234";

        DatabaseUserException ex = assertThrows(DatabaseUserException.class, () -> loginControl.authenticate(username, password));

        Assertions.assertEquals(DatabaseUserException.DatabaseUserExceptionType.USER_NOT_FOUND, ex.getDatabaseUserExceptionType());
        Assertions.assertEquals("No User could be found! Please check your credentials!", ex.getMessage());
    }
}
