package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpracheTest {
    @Autowired
    SprachenRepository sprachenRepository;

    @Test
    public void roundTripTest(){

        // Create
        Sprache sprache = new Sprache();
        sprache.setName("Testionisch");
        sprache.setLevel("B2");
        sprachenRepository.save(sprache);

        // Read
        Optional<Sprache> awaitSprache = sprachenRepository.findById(1);
        assertTrue(awaitSprache.isPresent());
        Sprache awaitedSprache = awaitSprache.get();
        assertEquals(awaitedSprache, sprache);

        // Update
        sprache.setLevel("C1");
        sprachenRepository.save(sprache);
        awaitSprache = sprachenRepository.findById(1);
        assertTrue(awaitSprache.isPresent());
        awaitedSprache = awaitSprache.get();
        assertEquals(awaitedSprache, sprache);

        // Delete
        sprachenRepository.deleteById(1);
        assertFalse(sprachenRepository.existsById(1));
    }
}
