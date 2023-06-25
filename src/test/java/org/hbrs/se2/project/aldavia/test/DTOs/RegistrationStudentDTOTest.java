package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOStudent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class RegistrationStudentDTOTest {
        @Test
        public void testEqualsAndHashCode() {
            RegistrationDTOStudent student1 = RegistrationDTOStudent.builder()
                    .userName("john123")
                    .password("password123")
                    .build();

            RegistrationDTOStudent student2 = RegistrationDTOStudent.builder()
                    .userName("john123")
                    .password("password123")
                    .build();

            RegistrationDTOStudent student3 = RegistrationDTOStudent.builder()
                    .userName("jane456")
                    .password("password456")
                    .build();

            // Test equality
            assertEquals(student1, student2);
            assertEquals(student1.hashCode(), student2.hashCode());

            // Test inequality
            assertNotEquals(student1, student3);
            assertNotEquals(student1.hashCode(), student3.hashCode());
        }

        @Test
        public void testToString() {
            RegistrationDTOStudent student = RegistrationDTOStudent.builder()
                    .userName("john123")
                    .password("password123")
                    .build();

            String expected = "UserDTO [UserName=john123, password=password123]";
            assertEquals(expected, student.toString());
        }

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

