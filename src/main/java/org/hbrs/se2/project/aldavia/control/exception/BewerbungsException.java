package org.hbrs.se2.project.aldavia.control.exception;

public class BewerbungsException extends Exception{
    public enum BewerbungsExceptionType {
        BEWERBUNG_NOT_FOUND,
        BEWERBUNG_COULD_NOT_BE_ADDED,
        BEWERBUNG_COULD_NOT_BE_DELETED, BEWERBUNG_ALREADY_EXISTS
    }

    private final BewerbungsExceptionType exceptionType;

    public BewerbungsException(String message, BewerbungsExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public BewerbungsExceptionType getExceptionType() {
        return exceptionType;
    }
}
