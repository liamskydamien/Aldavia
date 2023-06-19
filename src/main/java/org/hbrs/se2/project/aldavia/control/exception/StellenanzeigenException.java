package org.hbrs.se2.project.aldavia.control.exception;

public class StellenanzeigenException extends Exception{
    public enum StellenanzeigenExceptionType {
        STELLENANZEIGE_NOT_FOUND,
        STELLENANZEIGE_ALREADY_EXISTS
    }

    private final StellenanzeigenExceptionType type;

    public StellenanzeigenException(String message, StellenanzeigenExceptionType type) {
        super(message);
        this.type = type;
    }

    public StellenanzeigenExceptionType getType() {
        return type;
    }

    public String getReason() {
        return this.getMessage();
    }
}
