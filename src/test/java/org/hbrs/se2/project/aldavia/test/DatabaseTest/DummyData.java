package org.hbrs.se2.project.aldavia.test.DatabaseTest;

import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class DummyData {
        @Autowired
        private StudentRepository studentRepository;

        private Student student;
        private Kenntnis kenntnis;
        private Taetigkeitsfeld taetigkeitsfeld;
        private Sprache sprache;
        private Qualifikation qualifikation;

        @Test
        public void run(){
            User user = User.builder()
                    .userid("SinaSchmidt2001")
                    .password("TestPassword")
                    .email("Sina.Schmidt@aldavia.de")
                    .beschreibung("Ich bin eine Studentin.")
                    .phone("0123456789")
                    .build();

            student = Student.builder()
                    .vorname("Sina")
                    .nachname("Schmidt")
                    .geburtsdatum(LocalDate.of(2002, 3, 7))
                    .studiengang("Wirtschaftsinformatik")
                    .studienbeginn(LocalDate.of(2021, 10, 1))
                    .matrikelNummer("12345125678")
                    .lebenslauf("Sina Schmidt ist eine Studentin.")
                    .build();

            student.setUser(user);

            kenntnis = Kenntnis.builder()
                    .bezeichnung("Java")
                    .build();

            Kenntnis kenntnis2 = Kenntnis.builder()
                    .bezeichnung("C++")
                    .build();

            taetigkeitsfeld = Taetigkeitsfeld.builder()
                    .bezeichnung("Software Entwicklung")
                    .build();

            sprache = Sprache.builder()
                    .bezeichnung("Englisch")
                    .level("C1")
                    .build();

            Sprache sprache2 = Sprache.builder()
                    .bezeichnung("Deutsch")
                    .level("Muttersprache")
                    .build();

            qualifikation = Qualifikation.builder()
                    .beschreibung("Ich habe ein Praktikum bei Aldavia absolviert.")
                    .bereich("Software Entwicklung")
                    .bezeichnung("SaaS Entwickler")
                    .institution("Aldavia GmbH")
                    .von(LocalDate.of(2020, 1, 1))
                    .bis(LocalDate.of(2020, 7, 1))
                    .beschaftigungsverhaltnis("Praktikum")
                    .build();

            student.addKenntnis(kenntnis);
            student.addKenntnis(kenntnis2);
            student.addTaetigkeitsfeld(taetigkeitsfeld);
            student.addQualifikation(qualifikation);
            student.addSprache(sprache);
            student.addSprache(sprache2);

            student = studentRepository.save(student);
        }
}
