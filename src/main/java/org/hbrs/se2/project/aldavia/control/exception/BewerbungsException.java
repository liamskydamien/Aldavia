package org.hbrs.se2.project.aldavia.control.exception;

public class BewerbungsException extends Exception{
    public enum BewerbungsExceptionType {
        BEWERBUNG_NOT_FOUND,
        BEWERBUNG_ALREADY_EXISTS
    }

    private BewerbungsExceptionType exceptionType;

    public BewerbungsException(String message, BewerbungsExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public BewerbungsExceptionType getExceptionType() {
        return exceptionType;
    }
}
