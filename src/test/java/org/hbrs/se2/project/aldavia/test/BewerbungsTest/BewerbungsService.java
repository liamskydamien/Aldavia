package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class BewerbungsService {
    @Autowired
    private BewerbungsService bewerbungsService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    private Student studentEntity;
    private Unternehmen unternehmenEntity;
    private Stellenanzeige stellenanzeigeEntity;


    @BeforeEach
    public void setup() {

        User user = User.builder()
                .userid("testBewerbung")
                .password("test")
                .email("test@bewerbung-aldavia-student.de")
                .build();

        Student student = Student.builder()
                .user(user)
                .vorname("test")
                .nachname("test")
                .build();

        User user2 = User.builder()
                .userid("testBewerbung2")
                .password("test")
                .email("test@bewerbung-aldavia-unternehmen.de")
                .build();

        Unternehmen unternehmen = Unternehmen.builder()
                .user(user2)
                .name("test")
                .build();

        studentEntity = studentRepository.save(student);
        unternehmenEntity = unternehmenRepository.save(unternehmen);

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .start(LocalDate.now())
                .ende(LocalDate.now().plusDays(10))
                .beschreibung("test")
                .beschaeftigungsumfang("test")
                .beschaeftigungsverhaeltnis("test")
                .bezeichnung("test")
                .unternehmen_stellenanzeigen(unternehmenEntity)
                .build();

        stellenanzeigeEntity = stellenanzeigeRepository.save(stellenanzeige);
    }

    @AfterEach
    public void teardown() {
        studentRepository.delete(studentEntity);
        unternehmenRepository.delete(unternehmenEntity);
    }

    @Test
    public void testAddBewerbung() {
    }
}
