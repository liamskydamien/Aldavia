package org.hbrs.se2.project.aldavia.control.exception;

public enum Exceptions {
    NOUSERFOUND("NOUSERFOUND"),
    SQLERROR("SQLERROR"),
    DATABASE("DATABASE");

    private String reason = null;

    Exceptions(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
