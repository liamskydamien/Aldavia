package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpracheDTOTest {
    @Test
    public void testGettersAndSetters() {
        // Create an instance of SpracheDTO
        SpracheDTO sprache = new SpracheDTO();

        // Set values using setters
        sprache.setName("English");
        sprache.setLevel("Advanced");
        sprache.setId(1);

        // Test getters
        Assertions.assertEquals("English", sprache.getName());
        Assertions.assertEquals("Advanced", sprache.getLevel());
        Assertions.assertEquals(1, sprache.getId());
    }
}

