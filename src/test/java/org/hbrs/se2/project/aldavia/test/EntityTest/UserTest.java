package org.hbrs.se2.project.aldavia.test.EntityTest;

import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {
    @Test
    public void testMissingCode(){
        User user = User.builder()
                .id(1)
                .email("Test")
                .password("Test")
                .build();

        user.removeRolle(null);
        assertThrows(NullPointerException.class, () -> user.getRollen().size());
        Rolle rolle = Rolle.builder()
                .bezeichnung("Test")
                .build();
        user.addRolle(rolle);
        assertEquals(1, user.getRollen().size());
        user.removeRolle(rolle);
        assertEquals(0, user.getRollen().size());

        User user1 = User.builder()
                .id(1)
                .email("Test")
                .password("Test")
                .rollen(List.copyOf(user.getRollen()))
                .build();

        assertEquals(user, user1);
        assertNotEquals(user, new Object());
        assertNotEquals(user, User.builder().build());
        assertNotEquals(user, null);

    }
}
