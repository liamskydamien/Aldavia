package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KenntnisDTOTest {
    @Test
    public void testGettersAndSetters() {
        KenntnisDTO kenntnis = new KenntnisDTO();
        kenntnis.setName("Java");
        assertEquals("Java", kenntnis.getName());
    }
}
