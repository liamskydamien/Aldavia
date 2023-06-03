package org.hbrs.se2.project.aldavia.control.exception;

public class PersistenceException extends Exception{
    public enum PersistenceExceptionType {
        SPRACHE_NOT_FOUND,
        KENNTNIS_NOT_FOUND,
        QUALIFIKATION_NOT_FOUND,
        TAETIGKEITSFELD_NOT_FOUND,
    }

    private final PersistenceExceptionType persistenceExceptionType;

    /**
     * Konstruktor
     * @param persistenceExceptionType Typ der Exception
     * @param message Nachricht
     */
    public PersistenceException(PersistenceExceptionType persistenceExceptionType, String message) {
        super(message);
        this.persistenceExceptionType = persistenceExceptionType;
    }

    /**
     * Gibt den Typ der Exception zurück
     * @return DatabaseUserExceptionType
     */
    public PersistenceExceptionType getPersistenceExceptionType() {
        return persistenceExceptionType;
    }

    public String getReason() {
        return this.getMessage();
    }
}
