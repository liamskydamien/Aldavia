package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RollenTest {
    @Test
    public void testMissingCode(){
        Rolle rolle = Rolle.builder()
                .bezeichnung("Test")
                .build();

        User user = User.builder()
                .id(1)
                .email("Test")
                .password("Test")
                .build();

        rolle.removeUser(null);
        assertThrows(NullPointerException.class, () -> rolle.getUsers().size());
        rolle.addUser(user);
        assertEquals(1, rolle.getUsers().size());
        rolle.removeUser(user);
        assertEquals(0, rolle.getUsers().size());

        Rolle rolle1 = Rolle.builder()
                .bezeichnung("Test")
                .users(rolle.getUsers())
                .build();

        assertEquals(rolle, rolle1);
        assertNotEquals(rolle, new Object());
        assertNotEquals(rolle, Rolle.builder().build());
        assertNotEquals(rolle, null);
    }
}
