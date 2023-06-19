package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileExceptionTest {

    public static final String MESSAGE = "Wrong Exception thrown";
    public static final String TEST = "test";

    @Test
    public void testProfilException(){
        ProfileException profileException = new ProfileException(TEST, ProfileException.ProfileExceptionType.ERROR_CREATING_PROFILE_DTO);
        assertEquals(TEST, profileException.getMessage(), MESSAGE);
        assertEquals(ProfileException.ProfileExceptionType.ERROR_CREATING_PROFILE_DTO, profileException.getProfileExceptionType(), MESSAGE);
    }

    @Test
    public void testProfileException2(){
        ProfileException profileException = new ProfileException(TEST, ProfileException.ProfileExceptionType.STUDENT_DOES_NOT_EXIST);
        assertEquals(TEST, profileException.getMessage(), MESSAGE);
        assertEquals(ProfileException.ProfileExceptionType.STUDENT_DOES_NOT_EXIST, profileException.getProfileExceptionType(), MESSAGE);
    }

    @Test
    public void testProfileException3(){
        ProfileException profileException = new ProfileException(TEST, ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        assertEquals(TEST, profileException.getMessage(), MESSAGE);
        assertEquals(ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED, profileException.getProfileExceptionType(), MESSAGE);
    }

    @Test
    public void testProfileException4(){
        ProfileException profileException = new ProfileException(TEST, ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
        assertEquals(TEST, profileException.getMessage(), MESSAGE);
        assertEquals(ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND, profileException.getProfileExceptionType(), MESSAGE);
    }
}
