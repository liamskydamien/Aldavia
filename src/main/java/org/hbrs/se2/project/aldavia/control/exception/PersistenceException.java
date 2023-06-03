package org.hbrs.se2.project.aldavia.control.exception;

public class PersistenceException extends Exception{
    public enum PersistenceExceptionType {
        SpracheNotFound ,
        KenntnisNotFound ,
        QualifikationNotFound ,
        TaetigkeitsfeldNotFound ,
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
