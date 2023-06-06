package org.hbrs.se2.project.aldavia.control.exception;

public class DatabaseUserException extends Exception {
    public enum DatabaseUserExceptionType {
        USER_NOT_FOUND,
        DATABASE_CONNECTION_FAILED,
    }

    private final DatabaseUserExceptionType databaseUserExceptionType;

    /**
     * Konstruktor
     * @param databaseUserExceptionType Typ der Exception
     * @param message Nachricht
     */
    public DatabaseUserException(DatabaseUserExceptionType databaseUserExceptionType, String message) {
        super(message);
        this.databaseUserExceptionType = databaseUserExceptionType;
    }

    /**
     * Gibt den Typ der Exception zurück
     * @return DatabaseUserExceptionType
     */
    public DatabaseUserExceptionType getDatabaseUserExceptionType() {
        return databaseUserExceptionType;
    }

    public String getReason() {
        return this.getMessage();
    }

}
