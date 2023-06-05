package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class dummyData {
        @Autowired
        private StudentRepository studentRepository;

    @Test
        public void run(){
            User user = User.builder()
                    .userid("MaxMustermann2001")
                    .password("TestPassword")
                    .email("Max.Mustermann@aldavia.de")
                    .beschreibung("Ich studiere Wirtschaftsinformatik an der Hochschule Bonn-Rhein-Sieg. Ich bin auf der Suche nach einem Praktikum.")
                    .phone("0123456789")
                    .build();

            Student student = Student.builder()
                    .vorname("Max")
                    .nachname("Mustermann")
                    .geburtsdatum(LocalDate.of(2002, 3, 7))
                    .studiengang("Wirtschaftsinformatik")
                    .studienbeginn(LocalDate.of(2021, 10, 1))
                    .matrikelNummer("12345125678")
                    .lebenslauf("MaxMustermann.pdf")
                    .build();

            student.setUser(user);

            Kenntnis kenntnis = Kenntnis.builder()
                    .bezeichnung("Java")
                    .build();

            Kenntnis kenntnis2 = Kenntnis.builder()
                    .bezeichnung("C++")
                    .build();

            Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                    .bezeichnung("Software Entwicklung")
                    .build();

            Sprache sprache = Sprache.builder()
                    .bezeichnung("Englisch")
                    .level("C1")
                    .build();

            Sprache sprache2 = Sprache.builder()
                    .bezeichnung("Deutsch")
                    .level("Muttersprache")
                    .build();

            Qualifikation qualifikation = Qualifikation.builder()
                    .beschreibung("Ich habe ein Praktikum bei Aldavia absolviert.")
                    .bereich("Software Entwicklung")
                    .bezeichnung("SaaS Entwickler")
                    .institution("Aldavia GmbH")
                    .von(LocalDate.of(2020, 1, 1))
                    .bis(LocalDate.of(2020, 7, 1))
                    .beschaftigungsverhaltnis("Praktikum")
                    .build();

            Qualifikation qualifikation1 = Qualifikation.builder()
                    .beschreibung("Ich habe ein Praktikum bei NoCode als Testentwickler absolviert.")
                    .bereich("Software Entwicklung")
                    .bezeichnung("Test Entwickler")
                    .institution("NoCode GmbH")
                    .von(LocalDate.of(2020, 1, 1))
                    .bis(LocalDate.of(2020, 7, 1))
                    .beschaftigungsverhaltnis("Praktikum")
                    .build();

            student.addKenntnis(kenntnis);
            student.addKenntnis(kenntnis2);
            student.addTaetigkeitsfeld(taetigkeitsfeld);
            student.addQualifikation(qualifikation);
            student.addQualifikation(qualifikation1);
            student.addSprache(sprache);
            student.addSprache(sprache2);

            student = studentRepository.save(student);
        }
}
