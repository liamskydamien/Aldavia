package org.hbrs.se2.project.aldavia.control.exception;

public class DatabaseUserException extends Exception {
    public enum DatabaseUserExceptionType {
        UserNotFound,
        DatabaseConnectionFailed,
    }

    private final DatabaseUserExceptionType databaseUserExceptionType;

    /**
     * Konstruktor
     * @param databaseUserExceptionType
     * @param message
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

}
