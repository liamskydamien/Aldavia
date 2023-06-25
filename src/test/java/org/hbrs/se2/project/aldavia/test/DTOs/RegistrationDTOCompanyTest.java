package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOCompany;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegistrationDTOCompanyTest {
    @Test
    public void testEqualsAndHashCode() {
        RegistrationDTOCompany company1 = RegistrationDTOCompany.builder()
                .userName("companyA")
                .password("password123")
                .build();

        RegistrationDTOCompany company2 = RegistrationDTOCompany.builder()
                .userName("companyA")
                .password("password123")
                .build();

        RegistrationDTOCompany company3 = RegistrationDTOCompany.builder()
                .userName("companyB")
                .password("password456")
                .build();

        // Test equality
        Assertions.assertEquals(company1, company2);
        Assertions.assertEquals(company1.hashCode(), company2.hashCode());

        // Test inequality
        Assertions.assertNotEquals(company1, company3);
        Assertions.assertNotEquals(company1.hashCode(), company3.hashCode());
    }

    @Test
    public void testToString() {
        RegistrationDTOCompany company = RegistrationDTOCompany.builder()
                .companyName("companyA")
                .password("password123")
                .build();

        String expected = "UserDTO [UserName=companyA, password=password123]";
        Assertions.assertEquals(expected, company.toString());
    }

    @Test
    public void testGettersAndSetters() {
        // Create an instance of RegistrationDTOCompany
        RegistrationDTOCompany company = new RegistrationDTOCompany();

        // Set values using setters
        company.setUserName("companyA");
        company.setCompanyName("Example Company");
        company.setMail("info@example.com");
        company.setPassword("password123");
        company.setRegistrationDate(20230625);
        company.setWebseite("www.example.com");

        // Test getters
        Assertions.assertEquals("companyA", company.getUserName());
        Assertions.assertEquals("Example Company", company.getCompanyName());
        Assertions.assertEquals("info@example.com", company.getMail());
        Assertions.assertEquals("password123", company.getPassword());
        Assertions.assertEquals(20230625, company.getRegistrationDate());
        Assertions.assertEquals("www.example.com", company.getWebseite());
    }
}

