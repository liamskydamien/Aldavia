package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaetigkeitsfeldDTOTest {
    @Test
    public void testGettersAndSetters() {
        // Create an instance of TaetigkeitsfeldDTO
        TaetigkeitsfeldDTO taetigkeitsfeld = new TaetigkeitsfeldDTO();
        taetigkeitsfeld.setName("Software Engineering");
        assertEquals("Software Engineering", taetigkeitsfeld.getName());
    }
}
