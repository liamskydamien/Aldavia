package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


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
}
