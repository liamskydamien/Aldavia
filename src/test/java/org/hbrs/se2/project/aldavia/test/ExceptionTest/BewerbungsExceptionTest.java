package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BewerbungsExceptionTest {
    @Test
    public void testBewerbungsException() {
        BewerbungsException bewerbungsException = new BewerbungsException("test", BewerbungsException.BewerbungsExceptionType.BEWERBUNG_COULD_NOT_BE_ADDED);
        assertEquals("test", bewerbungsException.getMessage(), "Wrong Exception thrown");
        assertEquals(BewerbungsException.BewerbungsExceptionType.BEWERBUNG_COULD_NOT_BE_ADDED, bewerbungsException.getExceptionType(), "Wrong Exception thrown");
    }
}
