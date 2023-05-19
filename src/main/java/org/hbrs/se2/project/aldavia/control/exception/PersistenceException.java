package org.hbrs.se2.project.aldavia.control.exception;

public class PersistenceException extends Exception{
    public enum PersistenceExceptionType {
        SpracheNotFound,
        ErrorWhileCreatingSprache,
        ErrorWhileUpdatingSprache,
        ErrorWhileCreatingUser,
        ErrorWhileCreatingBewerbung,
        ErrorWhileCreatingStellenanzeige,
        DatabaseConnectionFailed, ErrorWhileDeletingSprache, ErrorWhileFetchingKenntnis, ErrorWhileRemovingStudentFromKenntnis, ErrorWhileAddingStudentToKenntnis, ErrorWhileFetchingQualifikation, ErrorWhileDeletingQualifikation, ErrorWhileAddingStudentToQualifikation, ErrorWhileRemovingStudentFromQualifikation, ErrorWhileFetchingTaetigkeitsfeld, ErrorWhileRemovingTaetigkeitsfeld, ErrorWhileAddingStudentToTaetigkeitsfeld, ErrorWhileRemovingStudentFromTaetigkeitsfeld, ErrorWhileAddingQualifikation, ErrorWhileDeletingKenntnis, StudentAlreadyInSprache, ErrorWhileUpdatingQualifikation,
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
