package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BewerbungsSetup {
    private BewerbungsSetup() {
    }

    private static final String TEST = "test";

    public static Map<String, Object> setup() {
        String userid = "testBewerbung";

        User user = User.builder()
                .userid(userid)
                .build();

        Unternehmen unternehmen = Unternehmen.builder()
                .id(1)
                .name(TEST)
                .user(user)
                .build();

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .id(1)
                .beschreibung(TEST)
                .unternehmen_stellenanzeigen(unternehmen)
                .bezeichnung(TEST)
                .taetigkeitsfelder(new ArrayList<>())
                .start(null)
                .ende(null)
                .erstellungsdatum(null)
                .bezahlung(TEST)
                .beschaeftigungsverhaeltnis(TEST)
                .bezeichnung(TEST)
                .build();


        Student student = Student.builder()
                .user(user)
                .matrikelNummer("123456")
                .id(1)
                .studiengang(TEST)
                .vorname(TEST)
                .nachname(TEST)
                .build();

        Bewerbung bewerbung = Bewerbung.builder()
                .id(1)
                .student(student)
                .datum(null)
                .status(null)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(TEST)
                .build();

        Bewerbung bewerbung2 = Bewerbung.builder()
                .id(2)
                .student(null)
                .datum(null)
                .status(null)
                .student(student)
                .stellenanzeige(stellenanzeige)
                .bewerbungsSchreiben(TEST)
                .build();

        List<Bewerbung> bewerbungen = new ArrayList<>();
        bewerbungen.add(bewerbung);
        bewerbungen.add(bewerbung2);
        student.setBewerbungen(bewerbungen);
        stellenanzeige.addBewerbung(bewerbung);
        stellenanzeige.addBewerbung(bewerbung2);
        unternehmen.addStellenanzeige(stellenanzeige);

        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("user", user);
        map.put("unternehmen", unternehmen);
        map.put("stellenanzeige", stellenanzeige);
        map.put("student", student);
        map.put("bewerbung", bewerbung);
        map.put("bewerbung2", bewerbung2);
        map.put("bewerbungen", bewerbungen);

        return map;
    }

}
