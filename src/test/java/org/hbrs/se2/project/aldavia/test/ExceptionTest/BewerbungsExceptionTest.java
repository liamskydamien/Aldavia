package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BewerbungsExceptionTest {

    public static final String MESSAGE = "Wrong Exception thrown";
    public static final String TEST = "test";

    @Test
    public void testBewerbungsException() {
        BewerbungsException bewerbungsException = new BewerbungsException(TEST, BewerbungsException.BewerbungsExceptionType.BEWERBUNG_COULD_NOT_BE_ADDED);
        assertEquals(TEST, bewerbungsException.getMessage(), MESSAGE);
        assertEquals(BewerbungsException.BewerbungsExceptionType.BEWERBUNG_COULD_NOT_BE_ADDED, bewerbungsException.getExceptionType(), MESSAGE);
    }
}
