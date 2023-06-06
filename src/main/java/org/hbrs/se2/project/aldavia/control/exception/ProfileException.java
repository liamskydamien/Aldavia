package org.hbrs.se2.project.aldavia.control.exception;

public class ProfileException extends Exception {
    public enum ProfileExceptionType {
        PROFILE_NOT_FOUND,
        DATABASE_CONNECTION_FAILED,
        STUDENT_DOES_NOT_EXIST,
    }

    private final ProfileExceptionType profileExceptionType;

    public ProfileException(String message, ProfileExceptionType profileExceptionType) {
        super(message);
        this.profileExceptionType = profileExceptionType;
    }

    public ProfileExceptionType getProfileExceptionType() {
        return profileExceptionType;
    }
    public String getReason() {
        return this.getMessage();
    }
}
