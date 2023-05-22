package org.hbrs.se2.project.aldavia.test.RegistrationTest;

import org.hbrs.se2.project.aldavia.control.RegistrationControl;
import org.hbrs.se2.project.aldavia.control.factories.UserFactory;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOStudent;
import org.hbrs.se2.project.aldavia.dtos.RegistrationResult;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

// Info: Für Company bzw. Unternehmen kann ich noch keine Tests schreiben, weil es noch nicht implementiert ist.

@SpringBootTest
class RegistrationTest {

    @Autowired
    private RegistrationControl registrationControl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    private List<RegistrationDTOStudent> studentsToClear = new ArrayList<>();

    @AfterEach
    public void tearDown() {
        for (RegistrationDTOStudent student : studentsToClear) {
            userRepository.deleteByUserid(student.getUserName());
        }
        studentsToClear.clear();
    }

    @Test
    public void createStudent() {
        RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_1", "abc123");
        studentsToClear.add(studentDto);
        RegistrationResult result = registrationControl.createStudent(studentDto);

        assertTrue(result.getResult());
        assertEquals(RegistrationResult.REGISTRATION_SUCCESSFULL, result.getReason());
        assertNotNull(userRepository.findUserByEmail(studentDto.getMail()));
        assertNotNull(studentRepository.findByUser(userRepository.findUserByEmail(studentDto.getMail()).get()));
    }

    @Test
    public void testSyntaxValidationEmail() {
        String[] validEmails = {"test.user_1@example.com", "test.user_1@example.co.uk", "testuser_1@bar.io"};
        String[] invalidEmails = {"test.user_1", "test.user_1@", "@testuser_1.com", "testuser_1@.io"};

        for (int i=0; i < validEmails.length; i++) {
            RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_"+i, "abc123");
            studentDto.setMail(validEmails[i]);
            studentsToClear.add(studentDto);
            RegistrationResult result = registrationControl.createStudent(studentDto);

            assertTrue(result.getResult());
            assertEquals(result, RegistrationResult.REGISTRATION_SUCCESSFULL);
        }

        for (int i=0; i < invalidEmails.length; i++) {
            RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_"+i, "abc123");
            studentDto.setMail(invalidEmails[i]);
            studentsToClear.add(studentDto);
            RegistrationResult result = registrationControl.createStudent(studentDto);

            assertFalse(result.getResult());
            assertEquals(result, RegistrationResult.EMAIL_INVALID);
        }
    }

    @Test
    public void usernameEmpty(){
        RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("", "abc123");
        studentDto.setMail("testuser_1@example.com");
        studentsToClear.add(studentDto);
        RegistrationResult result = registrationControl.createStudent(studentDto);

        assertFalse(result.getResult());
        assertEquals(result, RegistrationResult.USERNAME_INVALID);
    }

    @Test
    public void emailEmpty(){
        RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_1", "abc123");
        studentDto.setMail("");
        studentsToClear.add(studentDto);
        RegistrationResult result = registrationControl.createStudent(studentDto);

        assertFalse(result.getResult());
        assertEquals(result, RegistrationResult.EMAIL_INVALID);
    }

    @Test
    public void passwordEmpty(){
        RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_1", "");
        studentDto.setMail("testuser_1@example.com");
        studentsToClear.add(studentDto);
        RegistrationResult result = registrationControl.createStudent(studentDto);
        assertEquals(result, RegistrationResult.PASSWORD_MISSING);
        assertFalse(result.getResult());
    }

    @Test
    public void createStudentDuplicateEmail() {
        RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_1", "abc123");
        studentDto.setMail("TestUser_1@mail.de");
        studentsToClear.add(studentDto);

        RegistrationDTOStudent studentDto2 = UserFactory.createNewUserWithNameAndPassword("TestUser_2", "abc123");
        studentDto2.setMail("TestUser_1@mail.de");
        studentsToClear.add(studentDto2);

        registrationControl.createStudent(studentDto);
        RegistrationResult result = registrationControl.createStudent(studentDto2);

        assertFalse(result.getResult());
        assertEquals(RegistrationResult.EMAIL_ALREADY_EXISTS, result.getReason());
    }

    @Test
    public void createStudentDuplicateUsername() {
        RegistrationDTOStudent studentDto = UserFactory.createNewUserWithNameAndPassword("TestUser_1", "abc123");
        studentDto.setMail("TestUser_1@mail.de");
        studentsToClear.add(studentDto);

        RegistrationDTOStudent studentDto2 = UserFactory.createNewUserWithNameAndPassword("TestUser_1", "abc123");
        studentDto2.setMail("TestUser_2@mail.de");
        studentsToClear.add(studentDto2);

        registrationControl.createStudent(studentDto);
        RegistrationResult result = registrationControl.createStudent(studentDto2);

        assertFalse(result.getResult());
        assertEquals(RegistrationResult.USERNAME_ALREADY_EXISTS, result.getReason());
    }

    /*@Test
    void createUnternehmen() {
        RegistrationResult result = registrationControl.createUnternehmen(companyDto);

        assertTrue(result.getResult());
        assertEquals(RegistrationResult.REGISTRATION_SUCCESSFULL, result.getReason());
        assertNotNull(userRepository.findUserByEmail(companyDto.getMail()));
        assertNotNull(unternehmenRepository.findByUser(userRepository.findUserByEmail(companyDto.getMail()).get()));
    }*/

    /*@Test
    void createUnternehmenDuplicateEmail() {
        RegistrationResult result = registrationControl.createUnternehmen(companyDto);

        assertFalse(result.getResult());
        assertEquals(RegistrationResult.EMAIL_ALREADY_EXISTS, result.getReason());
    }*/
}