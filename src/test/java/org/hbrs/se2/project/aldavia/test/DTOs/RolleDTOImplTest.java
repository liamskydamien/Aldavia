package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.impl.RolleDTOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RolleDTOImplTest {

    private RolleDTOImpl rolleDTO;

    @BeforeEach
    void setUp() {
        rolleDTO = new RolleDTOImpl();
    }

    @Test
    void testGetBezeichnung() {
        assertNull(rolleDTO.getBezeichhnung());

        rolleDTO.setBezeichnung("Test");
        assertEquals("Test", rolleDTO.getBezeichhnung());
    }

    @Test
    void testToString() {
        rolleDTO.setBezeichnung("Test");
        assertEquals("RolleDTOImpl{bezeichnung='Test'}", rolleDTO.toString());
    }
}
