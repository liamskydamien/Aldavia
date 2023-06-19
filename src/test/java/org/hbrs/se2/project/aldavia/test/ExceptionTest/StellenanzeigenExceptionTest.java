package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StellenanzeigenExceptionTest {
    @Test
    public void createStellenanzeigeException(){
        StellenanzeigenException stellenanzeigenException = new StellenanzeigenException("test", StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_ALREADY_EXISTS);
        assertEquals("test", stellenanzeigenException.getMessage(), "Wrong Exception thrown");
        assertEquals(StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_ALREADY_EXISTS, stellenanzeigenException.getType(), "Wrong Exception thrown");
    }

    @Test
    public void createStellenanzeigeException2(){
        StellenanzeigenException stellenanzeigenException = new StellenanzeigenException("test", StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND);
        assertEquals("test", stellenanzeigenException.getMessage(), "Wrong Exception thrown");
        assertEquals(StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND, stellenanzeigenException.getType(), "Wrong Exception thrown");
    }
}
