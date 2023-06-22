package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class StellenanzeigenHinzufügen {

    @Autowired
    StellenanzeigeRepository stellenanzeigeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UnternehmenRepository unternehmenRepository;

    @Autowired
    TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Test
    void datenHinzufügen() {
        User u1 = User.builder()
                .userid("Thomas112")
                .password("12345678910")
                .beschreibung("Hi ich bin Thomas")
                .email("11123@gmail.com")
                .build();

        Taetigkeitsfeld t1 = Taetigkeitsfeld.builder()
                .bezeichnung("Java")
                .build();
        Taetigkeitsfeld t2 = Taetigkeitsfeld.builder()
                .bezeichnung("Software")
                .build();

        taetigkeitsfeldRepository.save(t1);
        taetigkeitsfeldRepository.save(t2);

        Unternehmen unternehmen1 = Unternehmen.builder()
                .name("Adesso111")
                .user(u1)
                .webseite("Adesso.de")
                .build();

        Stellenanzeige s1 = Stellenanzeige.builder()
                .bezeichnung("Java Praktikum")
                .beschreibung("Hier lernst du professionell Java.")
                .bezahlung("12,5€")
                .beschaeftigungsverhaeltnis("Praktikum")
                .start(LocalDate.of(2023, 05, 02))
                .ende(LocalDate.of(2023, 07, 02))
                .unternehmen_stellenanzeigen(unternehmen1)
                .beschaeftigungsumfang("Praktikum")
                .taetigkeitsfelder(List.of(t1,t2))
                .build();

        Stellenanzeige s2 = Stellenanzeige.builder()
                .bezeichnung("Tätigkeit RE")
                .beschreibung("Hier lernst du professionell RE.")
                .bezahlung("20,5€")
                .beschaeftigungsverhaeltnis("Vollzeit")
                .start(LocalDate.of(2023, 05, 02))
                .ende(LocalDate.of(2023, 07, 02))
                .unternehmen_stellenanzeigen(unternehmen1)
                .build();

        Stellenanzeige s3 = Stellenanzeige.builder()
                .bezeichnung("C++ Praktikum")
                .beschreibung("Hier lernst du professionell C++.")
                .bezahlung("18,5€")
                .beschaeftigungsverhaeltnis("Teilzeit")
                .start(LocalDate.of(2023, 05, 02))
                .ende(LocalDate.of(2023, 07, 02))
                .unternehmen_stellenanzeigen(unternehmen1)
                .build();

        unternehmenRepository.save(unternehmen1);
        stellenanzeigeRepository.save(s1);
        stellenanzeigeRepository.save(s2);
        stellenanzeigeRepository.save(s3);

    }




}
