package org.hbrs.se2.project.aldavia.test.Utils;

import org.hbrs.se2.project.aldavia.util.Utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestUtils {
    @Test
    public void testCreateRandomPassword(){
        String randomPassword = Utils.generateRandomPassword(10);
        assertEquals(10, randomPassword.length());
    }
}
