package org.hbrs.se2.project.aldavia.test.ProfileTest;

import com.vaadin.flow.shared.BrowserDetails;
import org.hbrs.se2.project.aldavia.control.SpracheControl;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.SpracheDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TesteSpracheControl {

    @Autowired
    private SpracheControl spracheControl;

    @Autowired
    private SprachenRepository sprachenRepository;

    private int id;

    @BeforeEach
    public void setup() {
        Sprache sprache = new Sprache();
        sprache.setName("Deutsch_Test");
        sprache.setLevel("Muttersprache");
        sprachenRepository.save(sprache);
        id = sprache.getSpracheId();


    }

    @AfterEach
    public void teardown() {
        sprachenRepository.deleteById(id);
    }

    @Test
    public void testeCreateSprache(){
        try{
            Sprache englisch = spracheControl.createSprache("Englisch_Test", "Muttersprache");
            assertTrue(sprachenRepository.findByNameAndLevel("Englisch_Test", "Muttersprache").isPresent());
            Sprache englisch2 = spracheControl.createSprache("Englisch_Test", "Muttersprache");
            assertEquals(englisch.getSpracheId(), englisch2.getSpracheId());
            sprachenRepository.deleteById(englisch.getSpracheId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testeUpdateSprache(){
        try{
            SpracheDTOImpl spracheDTO = new SpracheDTOImpl();
            spracheDTO.setBezeichnung("Englisch_Test");
            spracheDTO.setLevel("Muttersprache");
            Sprache englisch = spracheControl.updateSprache(spracheDTO, id);
            assertTrue(sprachenRepository.findByNameAndLevel("Englisch_Test", "Muttersprache").isPresent());
            assertEquals(englisch.getSpracheId(), id);
            assertEquals(englisch.getName(), "Englisch_Test");
            assertEquals(englisch.getLevel(), "Muttersprache");
            Optional<Sprache> sprache = sprachenRepository.findById(id);
            assertTrue(sprache.isPresent());
            Sprache englisch2 = sprache.get();
            assertEquals(englisch2.getSpracheId(), id);
            assertEquals(englisch2.getName(), "Englisch_Test");
            assertEquals(englisch2.getLevel(), "Muttersprache");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testeDelete(){
        try{
            SpracheDTOImpl spracheDTO = new SpracheDTOImpl();
            spracheDTO.setBezeichnung("Deutsch_Test");
            spracheDTO.setLevel("Muttersprache");
            spracheControl.deleteSprache(spracheDTO);
            assertTrue(sprachenRepository.findByNameAndLevel("Deutsch_Test", "Muttersprache").isEmpty());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testeAddStudent(){

    }

}
