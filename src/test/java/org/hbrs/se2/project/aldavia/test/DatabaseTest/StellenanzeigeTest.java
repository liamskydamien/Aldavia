package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.checkerframework.checker.units.qual.A;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class StellenanzeigeTest {
    //TODO: Fix this test
    //TODO: Add round trip test for Stellenanzeige
    //TODO: Test Constraints if unternehmen gets deleted (cascade) -> Stellenanzeige should get deleted too
    //TODO: Test add... and remove... methods

    @Autowired
    StellenanzeigeRepository stellenanzeigeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UnternehmenRepository unternehmenRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    BewerbungRepository bewerbungRepository;

    Taetigkeitsfeld t1 = Taetigkeitsfeld.builder()
            .bezeichnung("Softare Entwicklung")
            .build();
    Taetigkeitsfeld t2 = Taetigkeitsfeld.builder()
            .bezeichnung("RE")
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

    Student student1 = Student.builder()
            .nachname("Müller")
            .vorname("Sabrina")
            .user(u2)
            .matrikelNummer("123")
            .build();

    Stellenanzeige s1 = Stellenanzeige.builder()
            .bezeichnung("Java Praktikum")
            .beschreibung("Hier lernst du professionell Java.")
            .bezahlung("12,5€")
            .beschaeftigungsverhaeltnis("Praktikum")
            .start(LocalDate.of(2023,05,02))
            .ende(LocalDate.of(2023,07,02))
            .unternehmen_stellenanzeigen(unternehmen1)
            .beschaeftigungsumfang("Praktikum")
            .build();

    Stellenanzeige s2 = Stellenanzeige.builder()
            .bezeichnung("Tätigkeit RE")
            .beschreibung("Hier lernst du professionell RE.")
            .bezahlung("20,5€")
            .beschaeftigungsverhaeltnis("Vollzeit")
            .start(LocalDate.of(2023,05,02))
            .ende(LocalDate.of(2023,07,02))
            .unternehmen_stellenanzeigen(unternehmen1)
            .build();

    Stellenanzeige s3 = Stellenanzeige.builder()
            .bezeichnung("C++ Praktikum")
            .beschreibung("Hier lernst du professionell C++.")
            .bezahlung("18,5€")
            .beschaeftigungsverhaeltnis("Teilzeit")
            .start(LocalDate.of(2023,05,02))
            .ende(LocalDate.of(2023,07,02))
            .unternehmen_stellenanzeigen(unternehmen1)
            .build();




    @Test
    public void roundTrip() {
        unternehmenRepository.save(unternehmen1);
        //Create
        stellenanzeigeRepository.save(s1);
        int idS1 = s1.getId();
        Stellenanzeige s1Test = stellenanzeigeRepository.findById(idS1).get();

        assertEquals(s1.getId(), s1Test.getId());

        //Update
        s1.setBezeichnung("Java Job");
        stellenanzeigeRepository.save(s1);
        s1Test = stellenanzeigeRepository.findById(idS1).get();

        assertEquals(s1.getBezeichnung(),s1Test.getBezeichnung());

        //Delete
        stellenanzeigeRepository.deleteById(idS1);
        assertEquals(false, stellenanzeigeRepository.existsById(idS1));

        //Check ob Unternehmen nicht mitgelöscht ist
        assertEquals(true, unternehmenRepository.existsById(unternehmen1.getId()));

        //Löschen Unternehmen
        int idUser1 = u1.getId();
        unternehmenRepository.deleteById(unternehmen1.getId());

        assertEquals(false, unternehmenRepository.existsById(unternehmen1.getId()));

        //Prüfen ob User mit gelöscht ist

        assertEquals(false, userRepository.existsById(idUser1));

    }

    @Test
    public void testErstellen() {
        unternehmen1.addStellenanzeige(s1);
        unternehmen1.addStellenanzeige(s2);

        unternehmenRepository.save(unternehmen1);
        //Prüfen ob Unternehmen und Stellenazeigen gespeichert wurden

        assertEquals(unternehmenRepository.existsById(unternehmen1.getId()),true);
        assertEquals(stellenanzeigeRepository.existsById(s1.getId()),true);
        assertEquals(stellenanzeigeRepository.existsById(s2.getId()),true);

        //Check ob ausgewählte Werte stimmen
        Unternehmen awaitUnternehmen = unternehmenRepository.findById(unternehmen1.getId()).get();
        assertEquals(awaitUnternehmen.getBeschreibung(),unternehmen1.getBeschreibung());

        Stellenanzeige awaitS1 = stellenanzeigeRepository.findById(s1.getId()).get();
        Stellenanzeige awaitS2 = stellenanzeigeRepository.findById(s2.getId()).get();
        assertEquals(awaitS1.getBezahlung(),s1.getBezahlung());
        assertEquals(awaitS2.getBezahlung(),s2.getBezahlung());

        //Prüfen, ob Referenzen gesetzt
        List<Stellenanzeige> awaitList = awaitUnternehmen.getStellenanzeigen();
        assertEquals(awaitList.get(0),awaitS1);
        assertEquals(awaitList.get(1),awaitS2);

        Unternehmen awaitS1Ersteller = awaitS1.getUnternehmen_stellenanzeigen();
        Unternehmen awaitS2Ersteller = awaitS2.getUnternehmen_stellenanzeigen();
        assertEquals(awaitUnternehmen, awaitS1Ersteller);
        assertEquals(awaitUnternehmen, awaitS2Ersteller);


        //Prüfen ob Stellenanzeigen beim Löschen des Unternhemens mit gelöscht werden

        unternehmenRepository.deleteById(unternehmen1.getId());
        assertEquals(false, unternehmenRepository.existsById(unternehmen1.getId()));
        assertEquals(false, stellenanzeigeRepository.existsById(s1.getId()));
        assertEquals(false, stellenanzeigeRepository.existsById(s2.getId()));
    }

    @Test
    public void testTaetigkeitsfelder() {

        //Manuelle speicherung von Taetigkeitsfledern und Unternehmen BEVOR! die stellenanzeige gespeichert wird
        //Grund: kein CascadeType.all
        taetigkeitsfeldRepository.save(t1);
        taetigkeitsfeldRepository.save(t2);
        unternehmenRepository.save(unternehmen1);

        Taetigkeitsfeld awaitT1 = taetigkeitsfeldRepository.findById(t1.getId()).get();
        Taetigkeitsfeld awaitT2 = taetigkeitsfeldRepository.findById(t2.getId()).get();
        assertEquals(awaitT1.getBezeichnung(),t1.getBezeichnung());
        assertEquals(awaitT2.getBezeichnung(),t2.getBezeichnung());

        s1.addTaetigkeitsfeld(t1);
        s1.addTaetigkeitsfeld(t2);

        //Prüfen ob Taetigkeitsfelder mit gespeichert werden
        stellenanzeigeRepository.save(s1);
        Stellenanzeige awaitS1 = stellenanzeigeRepository.findById(s1.getId()).get();



        //Referenzen Prüfen
        List<Taetigkeitsfeld> awaitTaetigkeitsfelder = s1.getTaetigkeitsfelder();
        assertEquals(awaitTaetigkeitsfelder.get(0), awaitT1);
        assertEquals(awaitTaetigkeitsfelder.get(1), awaitT2);

        List<Stellenanzeige> awaitStellenanzeigenT1 = awaitT1.getStellenanzeigen();
        List<Stellenanzeige> awaitStellenanzeigenT2 = awaitT2.getStellenanzeigen();

        assertEquals(awaitStellenanzeigenT1.get(0),s1);
        assertEquals(awaitStellenanzeigenT2.get(0),s1);

        //Löschen
        stellenanzeigeRepository.deleteById(s1.getId());
        //Taetigkeitsfelder dürfen nicht mit gelöscht sein
        assertEquals(true,taetigkeitsfeldRepository.existsById(t1.getId()));
        assertEquals(true,taetigkeitsfeldRepository.existsById(t2.getId()));
        taetigkeitsfeldRepository.deleteById(t1.getId());
        taetigkeitsfeldRepository.deleteById(t2.getId());
        assertEquals(false, stellenanzeigeRepository.existsById(s1.getId()));
        assertEquals(false, taetigkeitsfeldRepository.existsById(t1.getId()));
        assertEquals(false, taetigkeitsfeldRepository.existsById(t2.getId()));


    }

    @Test
    public void testBewerbungen(){
        unternehmenRepository.save(unternehmen1);
        studentRepository.save(student1);
        stellenanzeigeRepository.save(s1);
        Bewerbung b1 = Bewerbung.builder()
                .student(student1)
                .stellenanzeige(s1)
                .datum(LocalDate.now())
                .build();

        Bewerbung b2 = Bewerbung.builder()
                .student(student1)
                .stellenanzeige(s1)
                .datum(LocalDate.now())
                .build();

        assertEquals(true,stellenanzeigeRepository.existsById(s1.getId()));

        bewerbungRepository.save(b1);
        bewerbungRepository.save(b2);


        Bewerbung awaitB1 = bewerbungRepository.findById(b1.getId()).get();
        Bewerbung awaitB2 = bewerbungRepository.findById(b2.getId()).get();

        //Zuweisen von den Bewerbungen zu s1
        s1.addBewerbung(b1);
        s1.addBewerbung(b2);

        List<Bewerbung> awaitBewerbungen = s1.getBewerbungen();
        assertEquals(awaitB1.getId(),awaitBewerbungen.get(0).getId());
        assertEquals(awaitB2.getId(),awaitBewerbungen.get(1).getId());

        Stellenanzeige awaitStelleB1 = awaitB1.getStellenanzeige();
        Stellenanzeige awaitStelleB2 = awaitB2.getStellenanzeige();

        assertEquals(awaitStelleB1.getId(),s1.getId());
        assertEquals(awaitStelleB2.getId(),s1.getId());

        //Löschen: Bewerbungen werden mitgelöscht, wegen cascadeTypeAll
        stellenanzeigeRepository.deleteById(s1.getId());
        assertEquals(false, stellenanzeigeRepository.existsById(s1.getId()));
        assertEquals(false, bewerbungRepository.existsById(b1.getId()));
        assertEquals(false, bewerbungRepository.existsById(b2.getId()));


        assertEquals(true,studentRepository.existsById(student1.getId()));
        studentRepository.deleteById(student1.getId());
        assertEquals(false, studentRepository.existsById(student1.getId()));

        assertEquals(true,unternehmenRepository.existsById(unternehmen1.getId()));
        unternehmenRepository.deleteById(unternehmen1.getId());
        assertEquals(false, unternehmenRepository.existsById(unternehmen1.getId()));







    }





}
