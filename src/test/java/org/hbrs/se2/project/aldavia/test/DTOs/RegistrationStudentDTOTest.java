package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOStudent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class RegistrationStudentDTOTest {
    @Test
    public void testGettersAndSetters() {
        // Create an instance of RegistrationDTOStudent
        RegistrationDTOStudent student = new RegistrationDTOStudent();

        // Set values using setters
        student.setUserName("john123");
        student.setVorname("John");
        student.setNachname("Doe");
        student.setMail("john@example.com");
        student.setPassword("password123");
        student.setRegistrationDate(20230625);

        // Test getters
        assertEquals("john123", student.getUserName());
        assertEquals("John", student.getVorname());
        assertEquals("Doe", student.getNachname());
        assertEquals("john@example.com", student.getMail());
        assertEquals("password123", student.getPassword());
        assertEquals(20230625, student.getRegistrationDate());
    }
}

