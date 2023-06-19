package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.StellenanzeigenException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StellenanzeigenExceptionTest {

    public static final String MESSAGE = "Wrong Exception thrown";
    public static final String TEST = "test";

    @Test
    public void createStellenanzeigeException(){
        StellenanzeigenException stellenanzeigenException = new StellenanzeigenException(TEST, StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_ALREADY_EXISTS);
        assertEquals(TEST, stellenanzeigenException.getMessage(), MESSAGE);
        assertEquals(StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_ALREADY_EXISTS, stellenanzeigenException.getType(), MESSAGE);
    }

    @Test
    public void createStellenanzeigeException2(){
        StellenanzeigenException stellenanzeigenException = new StellenanzeigenException(TEST, StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND);
        assertEquals(TEST, stellenanzeigenException.getMessage(), MESSAGE);
        assertEquals(StellenanzeigenException.StellenanzeigenExceptionType.STELLENANZEIGE_NOT_FOUND, stellenanzeigenException.getType(), MESSAGE);
    }
}
