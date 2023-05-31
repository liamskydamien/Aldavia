package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.AdresseRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UnternehmenTest {

    @Autowired
    UnternehmenRepository unternehmenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdresseRepository adresseRepository;

    Adresse adresse1 = Adresse.builder()
            .land("Deutschland")
            .ort("Bonn")
            .plz("53123")
            .hausnummer("22")
            .strasse("ABC-Straße")
            .build();

    Adresse adresse2 = Adresse.builder()
            .land("Deutschland1")
            .ort("Bonn1")
            .plz("531231")
            .hausnummer("221")
            .strasse("ABC-Straße1")
            .build();

    User u1 = User.builder()
            .email("121311324@web.de")
            .userid("Th1o12mas12")
            .password("123")
            .build();

    User u2 = User.builder()
            .email("12345@web.de")
            .userid("Sabrina1")
            .password("1234")
            .build();

    Unternehmen unternehmen1 = Unternehmen.builder()
            .name("Adesso1")
            .beschreibung("IT-Unternehmen")
            .user(u1)
            .build();

    Unternehmen unternehmen2 = Unternehmen.builder()
            .name("Telekom")
            .beschreibung("IT-Unternehmen")
            .user(u2)
            .build();

    @Test
    public void roundTripTest(){

        //save
        unternehmenRepository.save(unternehmen1);
        assertEquals(true, unternehmenRepository.existsById(unternehmen1.getId()));
        Unternehmen awaitUnternehmen = unternehmenRepository.findById(unternehmen1.getId()).get();
        assertEquals(unternehmen1.getName(), awaitUnternehmen.getName());

        //update
        unternehmen1.setName("T-Systems");
        unternehmenRepository.save(unternehmen1);

        awaitUnternehmen = unternehmenRepository.findById(unternehmen1.getId()).get();
        assertEquals(unternehmen1.getName(), awaitUnternehmen.getName());

        //delete
        unternehmenRepository.deleteById(unternehmen1.getId());
        assertEquals(false, unternehmenRepository.existsById(unternehmen1.getId()));

        //Prüfen ob User gelöscht
        assertEquals(false, userRepository.existsById(u1.getId()));
    }

    //Unternehmen-Stellenanzeigen wurde schon ausgiebig im Test: StellenanzeigeTest getestet
/*
    @Test
    public void unternehmenAdressen() {
        adresseRepository.save(adresse1);
        adresseRepository.save(adresse2);
        assertEquals(true, adresseRepository.existsById(adresse1.getId()));
        assertEquals(true, adresseRepository.existsById(adresse2.getId()));

        unternehmen1.addAdresse(adresse1);
        unternehmen1.addAdresse(adresse2);
        unternehmenRepository.save(unternehmen1);
        assertEquals(true, unternehmenRepository.existsById(unternehmen1.getId()));

        Unternehmen awaitUnternehmen = unternehmenRepository.findById(unternehmen1.getId()).get();
        Adresse awaitAdresse1 = adresseRepository.findById(adresse1.getId()).get();
        Adresse awaitAdresse2 = adresseRepository.findById(adresse2.getId()).get();

        //Get Adressen von Unternehmen
        Set<Adresse> awaitAdressen = awaitUnternehmen.getAdressen();
        assertEquals(awaitAdressen.get(0).getId(),awaitAdresse1.getId());
        assertEquals(awaitAdressen.get(1).getId(),awaitAdresse2.getId());

        adresse1.addUnternehmen(unternehmen1);
        adresse2.addUnternehmen(unternehmen2);

        List<Unternehmen> awaitUnternehmenA1 = awaitAdresse1.getUnternehmen();
        List<Unternehmen> awaitUnternehmenA2 = awaitAdresse2.getUnternehmen();
        assertEquals(awaitUnternehmenA1.get(0).getId(),awaitUnternehmen.getId());
        assertEquals(awaitUnternehmenA2.get(0).getId(),awaitUnternehmen.getId());

        //Löschen des Unternehmens
        unternehmenRepository.deleteById(unternehmen1.getId());
        assertEquals(false, unternehmenRepository.existsById(unternehmen1.getId()));
        //Adressen dürfen nicht gelöscht sein
        assertEquals(true, adresseRepository.existsById(adresse1.getId()));
        assertEquals(true, adresseRepository.existsById(adresse2.getId()));

        //Löschen der Adressen
        adresseRepository.deleteById(adresse1.getId());
        adresseRepository.deleteById(adresse2.getId());
        assertEquals(false, adresseRepository.existsById(adresse1.getId()));
        assertEquals(false, adresseRepository.existsById(adresse2.getId()));




    } */
}
