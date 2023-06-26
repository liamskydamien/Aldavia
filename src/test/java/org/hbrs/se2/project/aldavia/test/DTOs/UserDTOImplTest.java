package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.RolleDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.RolleDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.UserDTOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDTOImplTest {

    private UserDTOImpl userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTOImpl();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(0, userDTO.getId());
        assertNull(userDTO.getEmail());
        assertNull(userDTO.getPassword());
        assertNull(userDTO.getUserid());
        assertNull(userDTO.getPhone());
        assertNull(userDTO.getProfilePicture());
        assertNull(userDTO.getBeschreibung());
        assertNull(userDTO.getRoles());

        userDTO.setId(1);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setUserid("testuser");
        userDTO.setPhone("123456789");
        userDTO.setProfilePicture("profile.jpg");
        userDTO.setBeschreibung("Test user");
        List<RolleDTO> roles = new ArrayList<>();
        roles.add(new RolleDTOImpl("ROLE_ADMIN"));
        userDTO.setRoles(roles);

        assertEquals(1, userDTO.getId());
        assertEquals("test@example.com", userDTO.getEmail());
        assertEquals("password", userDTO.getPassword());
        assertEquals("testuser", userDTO.getUserid());
        assertEquals("123456789", userDTO.getPhone());
        assertEquals("profile.jpg", userDTO.getProfilePicture());
        assertEquals("Test user", userDTO.getBeschreibung());
        assertEquals(1, userDTO.getRoles().size());
        assertEquals("ROLE_ADMIN", userDTO.getRoles().get(0).getBezeichhnung());
    }
}
