package org.hbrs.se2.project.aldavia.test.ExceptionTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileExceptionTest {
    @Test
    public void testProfilException(){
        ProfileException profileException = new ProfileException("test", ProfileException.ProfileExceptionType.ERROR_CREATING_PROFILE_DTO);
        assertEquals("test", profileException.getMessage(), "Wrong Exception thrown");
        assertEquals(ProfileException.ProfileExceptionType.ERROR_CREATING_PROFILE_DTO, profileException.getProfileExceptionType(), "Wrong Exception thrown");
    }

    @Test
    public void testProfileException2(){
        ProfileException profileException = new ProfileException("test", ProfileException.ProfileExceptionType.STUDENT_DOES_NOT_EXIST);
        assertEquals("test", profileException.getMessage(), "Wrong Exception thrown");
        assertEquals(ProfileException.ProfileExceptionType.STUDENT_DOES_NOT_EXIST, profileException.getProfileExceptionType(), "Wrong Exception thrown");
    }

    @Test
    public void testProfileException3(){
        ProfileException profileException = new ProfileException("test", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        assertEquals("test", profileException.getMessage(), "Wrong Exception thrown");
        assertEquals(ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED, profileException.getProfileExceptionType(), "Wrong Exception thrown");
    }

    @Test
    public void testProfileException4(){
        ProfileException profileException = new ProfileException("test", ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
        assertEquals("test", profileException.getMessage(), "Wrong Exception thrown");
        assertEquals(ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND, profileException.getProfileExceptionType(), "Wrong Exception thrown");
    }
}
